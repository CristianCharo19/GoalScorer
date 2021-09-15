package com.davidcharo.goalscorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidcharo.goalscorer.databinding.CardViewFavoriteItemBinding
import com.davidcharo.goalscorer.databinding.CardViewUsersItemBinding
import com.davidcharo.goalscorer.model.Favorite
import com.squareup.picasso.Picasso

class FavoriteAdapter(
    private val onItemClicked: (Favorite) -> Unit,
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var listFavorite: MutableList<Favorite> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_favorite_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        holder.itemView.setOnClickListener{onItemClicked(listFavorite[position])}
    }

    override fun getItemCount(): Int {
        return listFavorite.size

    }

    fun appenItems(newItems: MutableList<Favorite>) {
        listFavorite.clear()
        listFavorite.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardViewFavoriteItemBinding.bind(view)
        fun bind(favorite: Favorite) {
            with(binding) {
                teamTextView.text = favorite.team.toString()
                leagueTextView.text = favorite.league.toString()
                if (favorite.urlPicture != null) {
                    Picasso.get().load(favorite.urlPicture).into(pictureImageView);
                }
            }
        }
    }
}
