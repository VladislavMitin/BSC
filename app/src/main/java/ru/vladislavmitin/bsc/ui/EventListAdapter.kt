package ru.vladislavmitin.bsc.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView
import ru.vladislavmitin.bsc.R
import ru.vladislavmitin.bsc.domain.Event
import ru.vladislavmitin.bsc.ui.views.GuestListView

class EventListAdapter(private val context: Context, private val events: List<Event>, private val action: (Int) -> Unit):
    RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(context, LayoutInflater.from(context).inflate(R.layout.card_event_list_item, parent, false), action)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    class EventViewHolder(private val context: Context, itemView: View, action: (Int) -> Unit): RecyclerView.ViewHolder(itemView) {
        var id: Int? = null
        private val eventImage: AppCompatImageView =
            itemView.findViewById(R.id.cardEventListItemImage)
        private val eventName: TextView = itemView.findViewById(R.id.cardEventListItemName)
        private val invitedPhoto: CircleImageView =
            itemView.findViewById(R.id.cardEventListItemInvitedPhoto)
        private val invitedName: TextView = itemView.findViewById(R.id.cardEventListItemInvitedName)
        private val guests: GuestListView = itemView.findViewById(R.id.cardEventListItemGuestList)
        private val mapButton: MaterialButton = itemView.findViewById(R.id.cardEventListItemMap)

        init {
            mapButton.setOnClickListener {
                id?.let { action(it) }
            }
        }

        fun bind(event: Event) {
            id = event.id
            eventName.text = event.name
            invitedName.text = context.resources.getString(R.string.card_event_list_item_invited_text, event.invited.name)

            event.guests?.let {
                guests.guests = it
                guests.visibility = View.VISIBLE
            }

            Glide.with(context)
                .load(event.image)
                .placeholder(R.drawable.ic_image_placeholder_164)
                .into(eventImage)

            Glide.with(context)
                .load(event.invited.image)
                .placeholder(R.drawable.ic_person_placeholder_48)
                .fitCenter()
                .into(invitedPhoto)
        }
    }
}