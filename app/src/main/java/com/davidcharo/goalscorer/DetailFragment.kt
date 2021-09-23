package com.davidcharo.goalscorer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.davidcharo.goalscorer.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso


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

        /*with(binding) {
            val response = args.response
            homeTeamTextView.text =
            descriptionTextView.text = event.strDescriptionEN
            if (event.strThumb != null) {
                Picasso.get().load(event.strThumb).into(imageView3)
            }
        }*/
        return root
    }

}