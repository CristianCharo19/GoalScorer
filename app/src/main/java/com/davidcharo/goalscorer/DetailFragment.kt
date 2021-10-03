package com.davidcharo.goalscorer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.davidcharo.goalscorer.databinding.FragmentDetailBinding
import com.davidcharo.goalscorer.model.Response
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
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding) {
            val response = args.response
            homeTeamTextView.text = response.teams?.home?.name
            scoreTextView.text = "${response.goals?.home} - ${response.goals?.away}"
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

    private fun formattedDate(response: Response): String {
        val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00")
        val formato2 = SimpleDateFormat("EEE d 'de' MMM yy")
        val fecha = formato.parse(response.fixture?.date)
        val formattedDate = formato2.format(fecha)
        return formattedDate
    }

}