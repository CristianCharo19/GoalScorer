package com.davidcharo.goalscorer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidcharo.goalscorer.databinding.FragmentScoreBinding
import com.davidcharo.goalscorer.model.Event
import com.davidcharo.goalscorer.model.EventsList
import com.davidcharo.goalscorer.server.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentScore : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var  teamsAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?,): View? {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        teamsAdapter = EventAdapter(onItemClicked = {onMovieItemClickecd(it)})

        binding.teamRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@FragmentScore.context)
            adapter = teamsAdapter
            setHasFixedSize(false)
        }
        loadTeams()

        return  root
    }

    private fun loadTeams() {


        ApiService.create()
            .getTopRated()
            .enqueue(object : Callback<EventsList> {
                override fun onFailure(call: Call<EventsList>, t: Throwable) {
                    Log.d("Eroor", t.message.toString())
                }

                override fun onResponse(call: Call<EventsList>, response: Response<EventsList>) {
                    if (response.isSuccessful){
                        val listEvents : MutableList<Event> = response.body()?.events as MutableList<Event>
                        teamsAdapter.appenItems(listEvents)
                    }
                }
            })
    }

    private fun onMovieItemClickecd(event: Event) {
        findNavController().navigate(FragmentScoreDirections.actionNavigationScoreToDetailFragment(event = event))
    }

}