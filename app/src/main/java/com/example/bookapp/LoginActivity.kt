package com.example.bookapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.bookapp.databinding.ActivityLoginBinding
import com.example.bookapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    //firebas
    private lateinit var firebaseAuth: FirebaseAuth

    //proress dialog
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebas
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress create| login
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, not have account
        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        //handle click
        binding.loginBtn.setOnClickListener {
            /* buoc
            * 1 input data
            * 2 validate data
            * 3 login - fb auth
            * 4 check user type
            * user - move to user dashboard
            * admin - move to admin dashboard */

            validateData()
        }

    }

    private var email = ""
    private var password = ""

    private fun validateData() {
        //inputdata
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //2 validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid email format", Toast.LENGTH_SHORT).show()

        }
        else if (password.isEmpty()){
            Toast.makeText(this,"Enter Password..",Toast.LENGTH_SHORT).show()

        } else{
            loginUser()
        }

    }

    private fun loginUser() {
        //3 login fb Auth

        //show proress
        progressDialog.setMessage("Loggin In..")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                checkUser()
            }
            .addOnFailureListener { e ->
                //failed login
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed duo to ${e.message} ",Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser() {
        //check user

        progressDialog.setMessage("Checking User")

        val  firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("User")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()

                    //get user type
                    val userType = snapshot.child("userType").value
                    if (userType == "user"){
                        //it simple user
                        startActivity(Intent(this@LoginActivity,MainActivity2::class.java))
                        finish()
                    }
                    else{
                        //it admin open admin
                        startActivity(Intent(this@LoginActivity,MainActivity2::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }
}