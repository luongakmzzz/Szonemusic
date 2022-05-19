package com.example.test_main_music_app

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.test_main_music_app.Model.AllCategory
import com.example.test_main_music_app.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.lang.Exception



class SettingsFragment : Fragment() {


    private lateinit var binding: FragmentSettingsBinding
    private lateinit var database: DatabaseReference

//    private lateinit var allCategory: AllCategory

//    var Image: Int = 0
    lateinit var uri: Uri
    lateinit var mStorage: StorageReference
    //SharedPref
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = FragmentSettingsBinding.inflate(inflater, container, false)
//          Code test upload ảnh lên thẳng firebase
//        mStorage = FirebaseStorage.getInstance().getReference("Uploads")
//
//
//          binding.btnAddImg.setOnClickListener {
//              val intent = Intent()
//              intent.setType("image/*")
//              intent.setAction(Intent.ACTION_GET_CONTENT)
//              startActivityForResult(Intent.createChooser(intent, "Select Image"), Image)
//
//          }

        binding.btnAddImg.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }
        binding.btnAddMusic.setOnClickListener {
            val intent = Intent()
            intent.type = "audio/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 2)
        }



        binding.btnAddAll.setOnClickListener {

            val musicActivity = activity as MainMusicActivity

            database = FirebaseDatabase.getInstance().getReference("Music")
            val key = database.push().key.toString()
            var Namemusic = binding.nameMainMusic.text.toString()
            var Author = binding.nameMainAuthor.text.toString()
            var imgUrl = binding.loadUrlImg.text.toString()
            var musicUrl = binding.loadUrlMusic.text.toString()
            val uid = musicActivity.getId()
            var allCategory = AllCategory(key, Namemusic, Author, imgUrl, musicUrl, uid = uid)
            database.child(key).setValue(allCategory).addOnSuccessListener {
                binding.nameMainMusic.text?.clear()
                binding.nameMainAuthor.text?.clear()
                binding.loadUrlImg.text?.clear()
                binding.loadUrlMusic.text?.clear()
                Toast.makeText(activity,"Successfully",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(activity,"Faild",Toast.LENGTH_SHORT).show()
            }
        }









        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        val uriTxt = binding.textviewimgselected
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                uri = data?.data!!
                binding.showImg.setImageURI(uri)
                mStorage = FirebaseStorage.getInstance().getReference("Uploads/")

                val file = mStorage.child(uri.lastPathSegment!!)
                file.putFile(uri).addOnSuccessListener {
                    file.downloadUrl.addOnSuccessListener {
                        binding.loadUrlImg.setText(it.toString())


                    }
                }
            }
            if(requestCode == 2){
                uri = data?.data!!
                mStorage = FirebaseStorage.getInstance().getReference("Uploads/")
                val file = mStorage.child(uri.lastPathSegment!!)
                file.putFile(uri).addOnSuccessListener {
                    file.downloadUrl.addOnSuccessListener {
                        binding.loadUrlMusic.setText(it.toString())


                    }
                }
            }

        }


    }





















    //Code test upload ảnh lên thẳng firebase
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val uriTxt = binding.textviewimgselected
//        if(resultCode == RESULT_OK){
//            if(requestCode == Image){
//                uri = data!!.data!!
//                uriTxt.text = uri.toString()
//                upload()
//            }
//        }
//
//    }
//
//    private fun upload(){
//        var mReference = mStorage.child(uri.lastPathSegment!!)
//        try{
//            mReference.putFile(uri).addOnSuccessListener {
//                    taskSnapShot: UploadTask.TaskSnapshot? -> var url = taskSnapShot!!.storage.downloadUrl
//                val imgtxt = binding.textviewsongselected
//                imgtxt.text = url.toString()
//                Toast.makeText(activity,"Succesfully upload img", Toast.LENGTH_LONG).show()
//            }
//        }catch (e: Exception){
//            Toast.makeText(activity,"Faild Upload",Toast.LENGTH_LONG).show()
//        }
//
//    }

}