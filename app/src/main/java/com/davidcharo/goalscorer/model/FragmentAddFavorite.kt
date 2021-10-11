package com.davidcharo.goalscorer.model

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.davidcharo.goalscorer.FavoriteAdapter
import com.davidcharo.goalscorer.R
import com.davidcharo.goalscorer.databinding.FragmentAddFavoriteBinding
import com.davidcharo.goalscorer.model.score.Favorite
import com.davidcharo.goalscorer.utils.EMPTY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class FragmentAddFavorite : Fragment() {
    private var _binding: FragmentAddFavoriteBinding? = null
    private lateinit var favoriteAdapter: FavoriteAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private var urlImage: String? = null
    private val REQUEST_IMAGE_CAPTURE = 1000


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.takePictureImageView.setImageBitmap(imageBitmap)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentAddFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root


        auth = Firebase.auth

        binding.takePictureImageView.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.favoriteButton.setOnClickListener {
            val team = binding.teamEditText.text.toString()
            val league = binding.leagueEditText.text.toString()
            cleanViews()

            if (team.isNotEmpty() && league.isNotEmpty()) {
                createUser(team, league)
            } else {
                Toast.makeText(requireContext(), getString(R.string.form_error), Toast.LENGTH_LONG).show()
                cleanViews()
            }
        }

        return root
    }

    private fun createUser(team: String, league: String) {
        val db = Firebase.firestore
        val document = db.collection("favorite").document()
        val id = document.id

        val storage = FirebaseStorage.getInstance()
        val pictureRef = storage.reference.child("favorite").child(id)

        binding.takePictureImageView.isDrawingCacheEnabled = true
        binding.takePictureImageView.buildDrawingCache()
        val bitmap = (binding.takePictureImageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = pictureRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            pictureRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val urlPicture = task.result.toString()
                with(binding) {
                    val favorite = Favorite(id, team, league, urlPicture)
                    db.collection("favorite").document(id).set(favorite)
                }
            }
        }
    }

    private fun cleanViews() {
        with(binding) {
            teamEditText.setText(EMPTY)
            leagueEditText.setText(EMPTY)
        }
    }


    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}