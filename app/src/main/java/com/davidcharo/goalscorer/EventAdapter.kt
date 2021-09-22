package com.davidcharo.goalscorer

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidcharo.goalscorer.databinding.EventListItemBinding
import com.davidcharo.goalscorer.model.Event
import com.squareup.picasso.Picasso


class EventAdapter(
    private val onItemClicked: (Event) -> Unit,
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {


    private var listEvents: MutableList<Event> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listEvents[position])
        holder.itemView.setOnClickListener { onItemClicked(listEvents[position]) }
    }

    override fun getItemCount(): Int = listEvents.size

    fun appenItems(newItems: MutableList<Event>) {
        listEvents.clear()
        listEvents.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = EventListItemBinding.bind(view)
        private val context: Context = binding.root.context
        @SuppressLint("SetTextI18n")
        fun bind(event: Event) {
            with(binding) {
                homeTeamTextView.text = event.strHomeTeam
                scoreTextView.text = "${event.intHomeScore} - ${event.intAwayScore}"
                awayTeamTextView.text = event.strAwayTeam
                if (event.strThumb != null) {
                    Picasso.get().load("https://www.thesportsdb.com/images/media/team/badge/c3xyv01580833252.png").into(pictureHomeTeamImageView)
                    Picasso.get().load("https://www.thesportsdb.com/images/media/team/badge/lwxcn71576156105.png").into(pictureAwayTeamImageView)

                }
            }
        }
    }
}
