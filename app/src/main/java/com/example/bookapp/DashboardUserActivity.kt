package com.example.bookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.databinding.ActivityDashboardAdminBinding
import com.example.bookapp.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardUserActivity : AppCompatActivity() {
    /* phần category em chưa xây dựng xong vì hơi gấp nên em vẫn
    để lại để tiện sau này xây dựng tiếp ạ */

    //view binding
    private lateinit var binding: ActivityDashboardUserBinding

    //fire base auth
    private lateinit var firebaseAuth: FirebaseAuth
    //rcv


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
           startActivity(Intent(this,MainActivity::class.java))
            finish()

        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser ==null){
            //not logged in go to main
         binding.subTitleTv.text = "Not Logged in"
        } else{
            //logged in, get show user
            val email = firebaseUser.email
            //set to tv
            binding.subTitleTv.text = email
        }
    }
}