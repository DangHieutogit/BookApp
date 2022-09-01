package com.example.bookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class ReadBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        val headingtitle : TextView = findViewById(R.id.TitleTv)
        val mainReads : TextView = findViewById(R.id.tvRead)
        val imgReads: ImageView = findViewById(R.id.imageIv)

        val bundle : Bundle?= intent.extras
        val TitleTv = bundle!!.getString("TitleTv")
        val imageIv = bundle!!.getInt("imageIv")
        val reads = bundle!!.getString("reads")


        headingtitle.text = TitleTv
        mainReads.text = reads
        imgReads.setImageResource(imageIv)

        val clickback = findViewById<ImageButton>(R.id.backib);

        val clickme = findViewById<ImageButton>(R.id.logoutBtn);

        clickme.setOnClickListener {
            Toast.makeText(this, "Da Thoat Tai khoan.. ", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        clickback.setOnClickListener {
            Toast.makeText(this, "Quay Lai..", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }

    }
}