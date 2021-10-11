package com.davidcharo.goalscorer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidcharo.goalscorer.databinding.ScoresListItemBinding
import com.davidcharo.goalscorer.model.score.Results
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


class ScoresAdapter(
    private val onItemClicked: (Results) -> Unit,
) : RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {

    private var listResults: MutableList<Results> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scores_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listResults[position])
        holder.itemView.setOnClickListener { onItemClicked(listResults[position]) }
    }

    override fun getItemCount(): Int = listResults.size

    fun appenItems(newItems: MutableList<Results>) {
        listResults.clear()
        listResults.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ScoresListItemBinding.bind(view)
        private val context: Context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(results: Results) {

            val formattedDate = formattedDate(results)

            with(binding) {
                homeTeamTextView.text = results.teams?.home?.name
                if (results.goals?.home == null && results.goals?.away == null) {
                    scoreTextView.text = "20:00"
                } else {
                    scoreTextView.text = "${results.goals.home} - ${results.goals.away}"
                }
                awayTeamTextView.text = results.teams?.away?.name

                dateTextView.text = formattedDate
                if (results.fixture?.date != null) {
                    Picasso.get().load(results.teams?.home?.logo).into(pictureHomeTeamImageView)
                    Picasso.get().load(results.teams?.away?.logo).into(pictureAwayTeamImageView)
                }
            }
        }

        private fun formattedDate(results: Results): String {
            val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00")
            val formato2 = SimpleDateFormat("EEE d 'de' MMM yy")
            val fecha = formato.parse(results.fixture?.date)
            val formattedDate = formato2.format(fecha)
            return formattedDate
        }
    }
}
