package com.example.bookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed(Runnable {
         checkUser()
       },2000) // thoi gian chuyen man hinh


    }

    private fun checkUser() {
       val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else{

            val ref = FirebaseDatabase.getInstance().getReference("User")
            ref.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object :ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {


                        //get user type
                        val userType = snapshot.child("userType").value
                        if (userType == "user"){
                            //it simple user
                            startActivity(Intent(this@SplashActivity,DashboardUserActivity::class.java))
                            finish()
                        }
                        else if(userType == "admin"){
                            //it admin open admin
                            startActivity(Intent(this@SplashActivity,DashboardAdminActivity::class.java))
                            finish()
                        }else{
                            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

}

/* user logged in
* 1) check if user logged in
* 2) check type of user*/