package com.davidcharo.goalscorer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidcharo.goalscorer.databinding.RatingListItemBinding
import com.davidcharo.goalscorer.model.rating.Rating
import com.davidcharo.goalscorer.model.rating.Standing
import com.squareup.picasso.Picasso

class RatingAdapter(
    private val onItemClicked: (Standing) -> Unit,
) : RecyclerView.Adapter<RatingAdapter.ViewHolder>() {


    private var listRating: MutableList<Standing> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rating_list_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listRating[position])
        holder.itemView.setOnClickListener { onItemClicked(listRating[position]) }
    }

    override fun getItemCount(): Int = listRating.size

    fun appenItems(newItems: MutableList<Standing>) {
        listRating.clear()
        listRating.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RatingListItemBinding.bind(view)
        private val context: Context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(standing: Standing) {


            with(binding) {

                        rankTextView.text = standing?.rank.toString()
                        if (standing.team?.logo != null) {
                            Picasso.get().load(standing.team?.logo).into(pictureLogoTeamImageView)
                        }
                        teamNameTextView.text = standing.team?.name
                        playTextView.text = standing.all?.played.toString()
                        winTextView.text = standing.all?.win.toString()
                        drawTextView.text = standing.all?.draw.toString()
                        loseTextView.text = standing.all?.lose.toString()
                        goalTextView.text = "${standing.all?.goals?.forX}-${standing.all?.goals?.against}"
                        goalDiffTextView.text = standing.goalsDiff.toString()
                        pointTextView.text = standing.points.toString()

                }

                /*awayTeamTextView.text = results.teams?.away?.name

                if (results.fixture?.date != null) {
                    Picasso.get().load(results.teams?.home?.logo).into(pictureHomeTeamImageView)
                    Picasso.get().load(results.teams?.away?.logo).into(pictureAwayTeamImageView)
                }*/
            }
        }

    }

