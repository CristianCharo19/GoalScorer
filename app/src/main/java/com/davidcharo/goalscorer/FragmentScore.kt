package com.davidcharo.goalscorer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidcharo.goalscorer.databinding.FragmentScoresBinding
import com.davidcharo.goalscorer.model.score.FixturesList
import com.davidcharo.goalscorer.model.score.Results
import com.davidcharo.goalscorer.server.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentScore : Fragment() {

    private var _binding: FragmentScoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var scoresAdapter: ScoresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentScoresBinding.inflate(inflater, container, false)
        val root: View = binding.root

        scoresAdapter = ScoresAdapter(onItemClicked = { onMovieItemClickecd(it) })

        binding.teamRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FragmentScore.context)
            adapter = scoresAdapter
            setHasFixedSize(false)
        }
        loadTeams()

        return root
    }

    private fun loadTeams() {

        val URL_API = "https://api-football-v1.p.rapidapi.com/v3/"
        ApiService.create(URL_API)
            .getTopRated()
            .enqueue(object : Callback<FixturesList> {
                override fun onFailure(call: Call<FixturesList>, t: Throwable) {
                    Log.d("Eroor", t.message.toString())
                }

                override fun onResponse(call: Call<FixturesList>, response: Response<FixturesList>) {
                    if (response.isSuccessful) {
                        val listResults: MutableList<Results> = response.body()?.response as MutableList<Results>
                        scoresAdapter.appenItems(listResults)
                    }
                }
            })
    }

    private fun onMovieItemClickecd(results: Results) {
        findNavController().navigate(FragmentScoreDirections.actionNavigationScoreToDetailFragment(results= results))
    }
}
