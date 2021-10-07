package com.davidcharo.goalscorer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidcharo.goalscorer.databinding.FragmentNewsBinding
import com.davidcharo.goalscorer.model.rating.Rating
import com.davidcharo.goalscorer.model.rating.RatingList
import com.davidcharo.goalscorer.model.rating.Standing
import com.davidcharo.goalscorer.server.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentNews : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var ratingAdapter: RatingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ratingAdapter = RatingAdapter (onItemClicked = { onMovieItemClickecd(it) })

        binding.ratingRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FragmentNews.context)
            adapter = ratingAdapter
            setHasFixedSize(false)
        }
        loadTeams()

        return root
    }

    private fun loadTeams() {

        val URL_API = "https://api-football-v1.p.rapidapi.com/v3/"
        ApiService.create(URL_API)
            .getTopRating()
            .enqueue(object : Callback<RatingList> {
                override fun onFailure(call: Call<RatingList>, t: Throwable) {
                    Log.d("Error", t.message.toString())
                }

                override fun onResponse(call: Call<RatingList>, response: Response<RatingList>) {
                    if (response.isSuccessful) {
                        val listRating: MutableList<Standing> = response.body()?.rating?.get(0)?.league?.standings?.get(0) as MutableList<Standing>
                        ratingAdapter.appenItems(listRating)
                    }
                }
            })
    }

    private fun onMovieItemClickecd(standing: Standing) {
     //   findNavController().navigate(FragmentScoreDirections.actionNavigationScoreToDetailFragment(rating = rating))
    }
}