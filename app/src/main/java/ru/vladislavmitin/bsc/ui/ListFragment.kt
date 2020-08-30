package ru.vladislavmitin.bsc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_event_list.view.*
import ru.vladislavmitin.bsc.R
import javax.inject.Inject

class ListFragment: BaseFragment() {
    companion object {
        const val tag = "ListFragment"
        fun newInstance() = ListFragment()
    }

    @Inject lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var background: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)

        background = view.eventListBackground
        recyclerView = view.eventListRecyclerView
        progressBar = view.eventListProgressBar

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        activity?.let {
            recyclerView.adapter = EventListAdapter(it, listOf()) { id ->
                toMap(id)
            }
        }

        viewModel = activity?.run {
            ViewModelProvider(viewModelStore, viewModelFactory)[MainViewModel::class.java]
        } ?: throw Exception("Invalid activity")

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if(it) View.VISIBLE else View.INVISIBLE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(background, it, Snackbar.LENGTH_LONG)
                .show()
        })

        viewModel.events.observe(viewLifecycleOwner, Observer {
            it?.let { events ->
                activity?.let { activity ->
                    recyclerView.adapter = EventListAdapter(activity, events) { id ->
                        toMap(id)
                    }
                }
            }

        })

        activity?.let {
            (it as MainActivity).run {
                this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                this.supportActionBar?.setDisplayShowHomeEnabled(true)
                this.supportActionBar?.title = "Вечеринки"
            }
        }

        return view
    }

    private fun toMap(id: Int) {
        viewModel.setCurrentEvent(id)
        activity?.let {
            (it as MainActivity).toDetail()
        }
    }
}