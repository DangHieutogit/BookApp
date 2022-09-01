package com.example.bookapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.bookapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    //binding
    private lateinit var binding:ActivityRegisterBinding

    //firebas
    private lateinit var firebaseAuth: FirebaseAuth

    //proress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebas
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog| register
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle back button click
        binding.backBtn.setOnClickListener {
            onBackPressed()//chuyen vao previous screen
        }
        //handle click, begin register
        binding.registerBtn.setOnClickListener {
            /*buoc
            * 1,Input Data
            * 2, validate data
            * 3, tao nick - firebase auth
            * 4,Save user info - fb rdatabase*/
            validateData()
        }

    }

    private var name = ""
    private var email = ""
    private var password = ""


    private fun validateData() {

        //1,Input Data
        name=binding.nameEt.text.toString().trim()
        email=binding.emailEt.text.toString().trim()
        password=binding.passwordEt.text.toString().trim()
      val cPassword = binding.cpasswordEt.text.toString().trim()


        //2 vl data
        if (name.isEmpty()){
            //thieu ten
            Toast.makeText(this,"Enter your name..",Toast.LENGTH_SHORT).show()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email pattern
            Toast.makeText(this,"Invalid Email Pattern..",Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            Toast.makeText(this,"Enter Password..",Toast.LENGTH_SHORT).show()
        }
        else if (cPassword.isEmpty()){
            Toast.makeText(this,"Confirm Password..",Toast.LENGTH_SHORT).show()
        }
        else if(password != cPassword){
            Toast.makeText(this,"Password khong giong nhau..",Toast.LENGTH_SHORT).show()
        }
        else{
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        //tao tai khoan

        progressDialog.setMessage("Tạo Tài Khoản.")
        progressDialog.show()

        //create user in firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //tao acc
                updateUserInfo()
            }
            .addOnFailureListener { e->
                //khong thanh con
                progressDialog.dismiss()
                Toast.makeText(this,"Failed creating account duo to ${e.message}",Toast.LENGTH_SHORT).show()

            }

    }

    private fun updateUserInfo() {
        //save user info
        progressDialog.setMessage("Saving user info.")

        //timestamp
        val timestamp = System.currentTimeMillis()

//        get current user vid

        val  uid = firebaseAuth.uid

        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["profileImage"] = "" //add emtype
        hashMap["userType"] = "user" //possible valuse
        hashMap["timestamp"]= timestamp


        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
            //user info saved
                progressDialog.dismiss()
                Toast.makeText(this,"Account created..",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity,MainActivity2::class.java))
                finish()

            }
            .addOnFailureListener { e->
                //failed adding
                progressDialog.dismiss()
                Toast.makeText(this,"Failed saving user info duo to ${e.message}",Toast.LENGTH_SHORT).show()

            }




    }
}