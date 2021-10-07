package com.davidcharo.goalscorer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidcharo.goalscorer.databinding.FragmentMoreBinding
import com.davidcharo.goalscorer.model.score.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FragmentMore : androidx.fragment.app.Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private lateinit var userAdapter: UserAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val root: View = binding.root



        userAdapter = UserAdapter(onItemClicked = { onDebtorItemClicked(it) })
        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = userAdapter
            setHasFixedSize(false)
        }

        loadFromServer()
        //loadFromLocal()

        return root
    }

    private fun loadFromServer() {
        val db = Firebase.firestore
        db.collection("users").get().addOnSuccessListener { result ->
            var listUsers: MutableList<User> = arrayListOf()
            for (document in result) {
                Log.d("nombre", document.data.toString())
                listUsers.add(document.toObject<User>())
            }
            userAdapter.appenItems(listUsers)
        }
    }


    private fun onDebtorItemClicked(debtor: User) {
        //findNavController().navigate(ListFragmentDirections.actionNavigationListToDetailFragment(debtor = debtor))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}