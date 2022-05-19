package com.example.test_main_music_app


import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.test_main_music_app.Model.AllCategory
import com.example.test_main_music_app.databinding.ActivityMusicPlayerBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.database.*
import kotlinx.coroutines.Runnable
import java.time.Duration
import java.util.concurrent.TimeUnit


class MusicPlayerActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    private lateinit var playbtn: Button

    private lateinit var pausebtn: Button

    private lateinit var prev: Button

    private lateinit var nexts: Button

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var allCategory: AllCategory

    private lateinit var firebaseDatabase: FirebaseDatabase

    private lateinit var databaseReference: DatabaseReference

    private lateinit var binding: ActivityMusicPlayerBinding

    private lateinit var seekbar: SeekBar

    private lateinit var playerposition: TextView

    private lateinit var playerduration: TextView

    private lateinit var arraymusic : ArrayList<AllCategory>

    private var duration: Int = 0

    private var currentIdex: Int = 0

    private lateinit var songExtra: Bundle




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        var audioUrl = allCategory.musicUrl.toString()
//        totalTime = mediaPlayer.duration
//        playerposition = binding.playPosition
//        playerduration = binding.playDuration
        mediaPlayer = MediaPlayer()
        playbtn = binding.playBtn
        pausebtn = binding.pauseBtn
        prev = binding.prevBtn
        nexts = binding.nextBtn
        seekbar = binding.seekBar

        val music = intent.extras?.getParcelable<AllCategory>("allcategory")







        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Music")


        mediaPlayer = MediaPlayer()
        if (music != null) {

            binding.titleMusicDetails.text = music.namemusic
            Glide.with(this).load(music.imageUrl).into(binding.imageMusicDetails)
            mediaPlayer.setDataSource(music.musicUrl)

        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        mediaPlayer.prepareAsync()

        playbtn.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
//                Toast.makeText(this@MusicPlayerActivity, "Audio is pause", Toast.LENGTH_LONG)
//                    .show()
                intilialiseSeekbar()
            } else {
                mediaPlayer.start()
//                Toast.makeText(this@MusicPlayerActivity, "Audio start", Toast.LENGTH_SHORT)
//                    .show()
            }
        }


//        prev.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//
//                if( < arraymusic.size - 1){
//                    currentIdex ++
//                }else{
//                    currentIdex = 0
//                }
//
//            }
//
//        })




        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

//
//    private fun convertFormat(duration: Int): String {
//          return String.format("%2d:%2d"
//          , TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
//          , TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
//          TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong())))
//    }
//


    }

    override fun onPrepared(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }

    private fun intilialiseSeekbar() {
        seekbar.max = mediaPlayer.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekbar.progress = mediaPlayer.currentPosition
                    handler.postDelayed(this, 0)
                } catch (e: Exception) {
                    seekbar.progress = 0
                }
            }
        }, 0)

    }
}