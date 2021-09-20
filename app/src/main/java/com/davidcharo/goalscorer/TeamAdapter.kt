package com.davidcharo.goalscorer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidcharo.goalscorer.databinding.TeamListItemBinding
import com.davidcharo.goalscorer.model.Team
import com.squareup.picasso.Picasso


class TeamAdapter (
    private  val onItemClicked: (Team) -> Unit,
) : RecyclerView.Adapter<TeamAdapter.ViewHolder>(){


    private  var listTeams: MutableList<Team> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bind(listTeams[position])
        holder.itemView.setOnClickListener{onItemClicked(listTeams[position])}
    }

    override fun getItemCount(): Int = listTeams.size

    fun appenItems(newItems: MutableList<Team>){
        listTeams.clear()
        listTeams.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TeamListItemBinding.bind(view)
        private val context: Context = binding.root.context
        fun bind(team: Team) {
            with(binding) {
                teamTextView.text = team.strTeam
                leagueTextView.text = team.strLeague
                sportTextView.text = team.strSport
                if (team.strTeamBadge!= null){
                    Picasso.get().load(team.strTeamBadge).into(pictureImageView)
                }
            }
        }
    }
}
