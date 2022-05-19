package com.example.test_main_music_app

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_main_music_app.Adapter.CategoryItemRecyclerViewAdapter
import com.example.test_main_music_app.Adapter.MainRecyclerViewAdapter
import com.example.test_main_music_app.Model.AllCategory
import com.example.test_main_music_app.databinding.FragmentHomeBinding
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    private var arrayMusic: ArrayList<AllCategory> = ArrayList()

    private lateinit var dbref: DatabaseReference

    private lateinit var musicRecyclerView: RecyclerView

    private lateinit var mainRecyclerAdapter: CategoryItemRecyclerViewAdapter


    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)






        musicRecyclerView = binding.mainrecyclerviewmusic
        musicRecyclerView = binding.mainrecyclerviewmusic1
        musicRecyclerView = binding.mainrecyclerviewmusic2
        binding.mainrecyclerviewmusic.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.mainrecyclerviewmusic1.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.mainrecyclerviewmusic2.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        musicRecyclerView.setHasFixedSize(true)

        arrayMusic = arrayListOf()
        getMusicData()

        mainRecyclerAdapter = CategoryItemRecyclerViewAdapter(HomeFragment(), arrayMusic) {
            val intent = Intent(activity, MusicPlayerActivity::class.java)
            intent.putExtra("allcategory", it)
            startActivity(intent)
        }



        return binding.root


    }

    private fun getMusicData() {

        dbref = FirebaseDatabase.getInstance().getReference("Music")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                arrayMusic.clear()
                if (snapshot.exists()) {
                    for (musicSnapshot in snapshot.children) {
                        val music = musicSnapshot.getValue(AllCategory::class.java)
                        arrayMusic.add(music!!)
                    }


                    binding.mainrecyclerviewmusic.adapter = mainRecyclerAdapter
                    binding.mainrecyclerviewmusic1.adapter = mainRecyclerAdapter
                    binding.mainrecyclerviewmusic2.adapter = mainRecyclerAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}