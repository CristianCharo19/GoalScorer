package com.davidcharo.goalscorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidcharo.goalscorer.databinding.CardViewUsersItemBinding
import com.davidcharo.goalscorer.model.score.User
import com.squareup.picasso.Picasso


class UserAdapter(
    private val onItemClicked: (User) -> Unit,
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var listUser: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_users_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener { onItemClicked(listUser[position]) }
    }

    override fun getItemCount(): Int {
        return listUser.size

    }

    fun appenItems(newItems: MutableList<User>) {
        listUser.clear()
        listUser.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardViewUsersItemBinding.bind(view)
        fun bind(user: User) {
            with(binding) {
                nameTextView.text = user.name.toString()
                emailTextView.text = user.email.toString()
                if (user.urlPicture != null) {
                    Picasso.get().load(user.urlPicture).into(pictureImageView)
                }
            }
        }
    }
}