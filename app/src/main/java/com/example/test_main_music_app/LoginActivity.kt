package com.example.test_main_music_app

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.test_main_music_app.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //Viewbinding
    private lateinit var binding:ActivityLoginBinding
    //ActionBar
    private lateinit var actionbar: ActionBar
    //ProgressDialog
    private lateinit var progressDialog:ProgressDialog

    //SharedPref



    //FireBaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
//        actionbar = supportActionBar!!
//        actionbar.title = "Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Pleas wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //SharePref


        //handle click, open register activity
        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


        //handle click, begin login
        binding.apply {
            binding.loginbutton.setOnClickListener{
                //befor logging in, validate data
                validateData()

            }
        }

    }

    private fun validateData() {
       //get data
        email = binding.emailet.text.toString().trim()
        password = binding.passwordet.text.toString().trim()

       //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailet.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordet.error = "Please enter password"
        }
        else{
            //data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login succes
                progressDialog.dismiss()
                //get user info
                val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
                val editor = sharedPref.edit()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                val uid = firebaseUser!!.uid

                editor.putString("uid", uid)
                editor.apply()


                Toast.makeText(this, "Uid saving", Toast.LENGTH_LONG).show()
                
                Toast.makeText(this, "Logged In as  $email", Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this, MainMusicActivity::class.java))

                finish()
            }
            .addOnFailureListener { e->
                //login faild
                progressDialog.dismiss()
                Toast.makeText(this, "Login faild due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser(){
        //if user already logged in to go profile activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
              //user is already logged in
              startActivity(Intent(this, MainMusicActivity::class.java))

//              supportFragmentManager.beginTransaction().add(R.id.mainrecyclerviewmusic, HomeFragment()).commit()
              finish()
        }
    }
}