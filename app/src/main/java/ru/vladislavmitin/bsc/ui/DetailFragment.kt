package ru.vladislavmitin.bsc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import ru.vladislavmitin.bsc.R
import ru.vladislavmitin.bsc.domain.Event
import javax.inject.Inject

class DetailFragment: BaseFragment(), OnMapReadyCallback {
    companion object {
        const val tag = "DetailFragment"
        fun newInstance() = DetailFragment()

        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    @Inject lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var name: TextView
    private lateinit var address: TextView
    private lateinit var map: MapView

    private var currentEvent: Event? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)

        name = view.findViewById(R.id.eventDetailName)
        address = view.findViewById(R.id.eventDetailAddress)
        map = view.findViewById(R.id.eventDetailMap)

        viewModel = activity?.run {
            ViewModelProvider(viewModelStore, viewModelFactory)[MainViewModel::class.java]
        } ?: throw Exception("Invalid activity")

        viewModel.currentEvent.observe(viewLifecycleOwner, Observer {
            it?.let {
                currentEvent = it

                name.text = it.name
                address.text = it.location.address
            }
        })

        activity?.let {
            (it as MainActivity).run {
                this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                this.supportActionBar?.setDisplayShowHomeEnabled(true)
                this.supportActionBar?.title = "Вечеринка"
            }
        }

        var mapViewBundle: Bundle? = null

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        map.isClickable = false
        map.onCreate(mapViewBundle)
        map.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            it.uiSettings.isMapToolbarEnabled = false
            it.uiSettings.isMyLocationButtonEnabled = false

            val boundsDistance = 0.0015

            currentEvent?.let { event ->
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            event.location.latitude,
                            event.location.longitude
                        )
                    )
                )
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        LatLngBounds(
                            LatLng(
                                event.location.latitude - boundsDistance,
                                event.location.longitude - boundsDistance
                            ),
                            LatLng(
                                event.location.latitude + boundsDistance,
                                event.location.longitude + boundsDistance
                            )
                        ), 0
                    )
                )
            }
        }
    }
}