package com.davidcharo.goalscorer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.davidcharo.goalscorer.databinding.FragmentDetailBinding
import com.davidcharo.goalscorer.model.score.Results
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val args: DetailFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding) {
            val response = args.results
            homeTeamTextView.text = response.teams?.home?.name
            if (response.goals?.home == null && response.goals?.away == null){
                scoreTextView.text = getString(R.string.game_time)
            }else {
                scoreTextView.text = "${response.goals?.home} - ${response.goals?.away}"
            }
            awayTeamTextView.text = response.teams?.away?.name
            dateTextView.text = formattedDate(response)
            nameLeagueTextView.text = "${response.league?.name} ${response.league?.round}"
            nameRefereeTextView.text = response.fixture?.referee
            nameVenueTextView.text = response.fixture?.venue?.name

            if (response.league?.logo != null) {
                Picasso.get().load(response.teams?.home?.logo).into(pictureHomeTeamImageView)
                Picasso.get().load(response.teams?.away?.logo).into(pictureAwayTeamImageView)
                Picasso.get().load(response.league?.logo).into(pictureLeagueImageView)
            }
        }
        return root
    }

    private fun formattedDate(results: Results): String {
        val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00")
        val formato2 = SimpleDateFormat("EEE d 'de' MMM yy")
        val fecha = formato.parse(results.fixture?.date)
        val formattedDate = formato2.format(fecha)
        return formattedDate
    }

}