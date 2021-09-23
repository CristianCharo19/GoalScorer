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
import com.davidcharo.goalscorer.model.FixturesList
import com.davidcharo.goalscorer.server.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentScore : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var responseAdapter: ResponseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        responseAdapter = ResponseAdapter(onItemClicked = { onMovieItemClickecd(it) })

        binding.teamRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FragmentScore.context)
            adapter = responseAdapter
            setHasFixedSize(false)
        }
        loadTeams()

        return root
    }

    private fun loadTeams() {


        ApiService.create()
            .getTopRated()
            .enqueue(object : Callback<FixturesList> {
                override fun onFailure(call: Call<FixturesList>, t: Throwable) {
                    Log.d("Eroor", t.message.toString())
                }

                override fun onResponse(call: Call<FixturesList>, response: Response<FixturesList>) {
                    if (response.isSuccessful) {
                        val listResponse: MutableList<com.davidcharo.goalscorer.model.Response> = response.body()?.response as MutableList<com.davidcharo.goalscorer.model.Response>
                        responseAdapter.appenItems(listResponse)
                    }
                }
            })
    }

    private fun onMovieItemClickecd(response: com.davidcharo.goalscorer.model.Response) {
        findNavController().navigate(FragmentScoreDirections.actionNavigationScoreToDetailFragment(response = response))
    }
}
