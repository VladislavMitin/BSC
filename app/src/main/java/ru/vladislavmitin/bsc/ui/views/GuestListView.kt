package ru.vladislavmitin.bsc.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import ru.vladislavmitin.bsc.R
import ru.vladislavmitin.bsc.domain.Guest

class GuestListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val openButton: AppCompatImageView
    private val guestList: LinearLayoutCompat

    private val guestViews: MutableList<GuestView> = mutableListOf()

    private var isListOpen: Boolean = false

    private val alwaysVisibleCount = 2

    var guests: List<Guest> = listOf()
        set(value) {
            field = value
            initViews()

            openButton.visibility = if(value.size <= alwaysVisibleCount) View.INVISIBLE else View.VISIBLE
        }

    private fun initViews() {
        for(i in guests.indices) {
            guestViews.add(GuestView(context))
        }

        val guests = guests.take(alwaysVisibleCount)

        for(i in guests.indices) {
            guestViews[i].bind(guests[i])
            guestList.addView(guestViews[i])
        }
    }

    init {
        View.inflate(context, R.layout.view_guest_list, this)

        openButton = findViewById(R.id.guestListOpen)
        guestList = findViewById(R.id.guestListGuests)

        openButton.setOnClickListener {
            isListOpen = !isListOpen
            openButton.setImageResource(if(isListOpen) R.drawable.ic_arrow_up_24 else R.drawable.ic_arrow_down_24)
            showGuests()
        }
    }

    private fun showGuests() {
        if (isListOpen) {
            for (i in alwaysVisibleCount until guests.size) {
                guestViews[i].bind(guests[i])
                guestList.addView(guestViews[i])
            }
        } else {
            guestList.removeViewsInLayout(
                alwaysVisibleCount,
                guests.size - alwaysVisibleCount
            )

            guestList.requestLayout()
        }
    }

    class GuestView(context: Context) : ConstraintLayout(context) {
        private val photo: CircleImageView
        private val name: TextView

        init {
            inflate(context, R.layout.view_guest_list_item, this)

            photo = findViewById(R.id.guestListItemPhoto)
            name = findViewById(R.id.guestListItemName)
        }

        fun bind(guest: Guest) {
            name.text = guest.name

            Glide.with(context)
                .load(guest.image)
                .placeholder(R.drawable.ic_person_placeholder_48)
                .fitCenter()
                .into(photo)
        }
    }
}