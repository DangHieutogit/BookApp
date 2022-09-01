package com.example.bookapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bookapp.databinding.ActivityCategoryAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CategoryAddActivity : AppCompatActivity() {

    /* phần category em chưa xây dựng xong vì hơi gấp nên em vẫn
     để lại để tiện sau này xây dựng tiếp ạ */

    //viewbinding
    private lateinit var binding : ActivityCategoryAddBinding
    //fb auth
    private lateinit var firebaseAuth: FirebaseAuth
    //dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()

        //configure dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click, begin upload category

        binding.submitBtn.setOnClickListener {
            validateData()
        }

    }
    private var category = ""

    private fun validateData() {
        //validatedata


        //get data
        category = binding.categoryEt.text.toString().trim()

        //validate
        if (category.isEmpty()){
            Toast.makeText(this,"Enter Category",Toast.LENGTH_SHORT).show()
        }
        else{
            addCategoryFirebase()
        }
    }

    private fun addCategoryFirebase() {
        //show
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add fb
        val hashMap = HashMap<String,Any>()
        hashMap["id"] = "$timestamp"
        hashMap["category"] = category
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

        //add to firebase db db root> categories > category
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                //add succes
                progressDialog.dismiss()
                Toast.makeText(this,"Added succesfully",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                //failed to
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to add duo to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }
}