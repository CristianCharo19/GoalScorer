package com.davidcharo.goalscorer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidcharo.goalscorer.databinding.ResponseListItemBinding
import com.davidcharo.goalscorer.model.Response
import com.squareup.picasso.Picasso
import java.text.Format
import java.text.SimpleDateFormat


class ResponseAdapter(
    private val onItemClicked: (Response) -> Unit,
) : RecyclerView.Adapter<ResponseAdapter.ViewHolder>() {


    private var listResponse: MutableList<Response> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.response_list_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listResponse[position])
        holder.itemView.setOnClickListener { onItemClicked(listResponse[position]) }
    }

    override fun getItemCount(): Int = listResponse.size

    fun appenItems(newItems: MutableList<Response>) {
        listResponse.clear()
        listResponse.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ResponseListItemBinding.bind(view)
        private val context: Context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(response: Response) {

            val formattedDate = formattedDate(response)

            with(binding) {
                homeTeamTextView.text = response.teams?.home?.name
                scoreTextView.text = "${response.goals?.home} - ${response.goals?.away}"
                awayTeamTextView.text = response.teams?.away?.name

                dateTextView.text = formattedDate
                if (response.fixture?.date != null) {
                    Picasso.get().load(response.teams?.home?.logo).into(pictureHomeTeamImageView)
                    Picasso.get().load(response.teams?.away?.logo).into(pictureAwayTeamImageView)
                }
            }
        }

        private fun formattedDate(response: Response): String {
            val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00")
            val formato2 = SimpleDateFormat("EEE d 'de' MMM yy")
            val fecha = formato.parse(response.fixture?.date)
            val formattedDate = formato2.format(fecha)
            return formattedDate
        }
    }
}
