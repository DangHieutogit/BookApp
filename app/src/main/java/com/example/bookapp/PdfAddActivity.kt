package com.example.bookapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bookapp.databinding.ActivityPdfAddBinding
import com.google.firebase.auth.FirebaseAuth

class PdfAddActivity : AppCompatActivity() {
    /* phần category em chưa xây dựng xong vì hơi gấp nên em vẫn
    để lại để tiện sau này xây dựng tiếp ạ */

    private lateinit var binding : ActivityPdfAddBinding
    //firebase
    private lateinit var firebaseAuth: FirebaseAuth
    //dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init fb
        firebaseAuth = FirebaseAuth.getInstance()
    }
}