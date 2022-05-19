package com.example.test_main_music_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.test_main_music_app.databinding.ActivityMainMusicBinding
import com.example.test_main_music_app.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    //viewb inding
    private lateinit var binding: ActivityProfileBinding
//    private lateinit var binding1: ActivityMainMusicBinding
    //Action bar
    private lateinit var actionbar: ActionBar
    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    //SharedPref
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
//        binding1 = ActivityMainMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SharedPref



        //configure action bar
        actionbar = supportActionBar!!
        actionbar.title = "Profile"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        //handle click logout
        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
            val settings: SharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)
            settings.edit().clear().commit()
        }
        //handle click test main
        binding.Home.setOnClickListener {
            startActivity(Intent(this, MainMusicActivity::class.java))
        }
        //handle click test music
        binding.testmusicplayer.setOnClickListener {
            startActivity(Intent(this,MusicPlayerActivity::class.java))
        }

    }

    private fun checkUser() {
        //check user is logged in  or not
        val firebaseAuth = firebaseAuth.currentUser
        if(firebaseAuth != null){
            //user not null, user is logged in
            val email = firebaseAuth.email
            preferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val uid = preferences.getString("uid", "")

            //set to text view
            binding.emailTv.text = email
            binding.uidTv.text = uid

        }
        else{
            //user is null, user is not logged in
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}