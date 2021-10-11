package com.davidcharo.goalscorer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidcharo.goalscorer.databinding.FragmentFavoriteBinding
import com.davidcharo.goalscorer.model.score.Favorite
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class FragmentFavorite : androidx.fragment.app.Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var favoriteAdapter: FavoriteAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_favorite_to_fragmentAddFavorite)
        }



        binding.swiperefresh.setOnRefreshListener {
            loadFromServer()
            binding.swiperefresh.isRefreshing = false
        }


        favoriteAdapter = FavoriteAdapter(onItemClicked = { onFavoriteItemClicked(it) })
        binding.favoriteRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = favoriteAdapter
            setHasFixedSize(false)
        }
        loadFromServer()

        return root
    }

    private fun loadFromServer() {
        val db = Firebase.firestore
        db.collection("favorite").get().addOnSuccessListener { result ->
            var listFavorites: MutableList<Favorite> = arrayListOf()
            for (document in result) {
                Log.d("nombre", document.data.toString())
                listFavorites.add(document.toObject<Favorite>())
            }
            favoriteAdapter.appenItems(listFavorites)
        }
    }


    private fun onFavoriteItemClicked(favorite: Favorite) {
        //findNavController().navigate(ListFragmentDirections.actionNavigationListToDetailFragment(debtor = debtor))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}