package com.example.bookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardUserBinding

    //fire base auth
    private lateinit var firebaseAuth: FirebaseAuth
    //rcv

    private lateinit var newRcv: RecyclerView
    private lateinit var newArrayList: ArrayList<Book>
    lateinit var imageIv: Array<Int>
    lateinit var TitleTv: Array<String>
    lateinit var descriptionTv: Array<String>
    lateinit var reads: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        imageIv = arrayOf(
            R.drawable.ic_thanhgiong,
            R.drawable.ic_tamcam,
            R.drawable.ic_sodua,
            R.drawable.ic_bachtuyet,
            R.drawable.ic_caytretramdot,
            R.drawable.ic_cayke
        )
        TitleTv = arrayOf(
            "Thánh Gióng",
            "Tấm Cám",
            "Sọ Dừa",
            "Bạch Tuyết",
            "Cây Tre Trăm Đốt",
            "Cây Khế",
        )
        descriptionTv = arrayOf(
            "Thánh Gióng",
            "Tấm Cám",
            "Sọ Dừa",
            "Bạch Tuyết",
            "Cây Tre Trăm Đốt",
            "Cây Khế",

            )

        reads = arrayOf(
            getString(R.string.reads_thanhgiong),
            getString(R.string.reads_tamcam),
            getString(R.string.reads_sodua),
            getString(R.string.reads_bachtuyet),
            getString(R.string.reads_Caytre),
            getString(R.string.reads_caykhe)

        )


        newRcv = findViewById(R.id.RcvData)
        newRcv.layoutManager = LinearLayoutManager(this)
        newRcv.setHasFixedSize(true)

        newArrayList = arrayListOf<Book>()


        getUserData()


    }

    private fun getUserData() {

        for (i in imageIv.indices) {
            val book = Book(TitleTv[i], imageIv[i], descriptionTv[i])
            newArrayList.add(book)
        }

        var adapter = BookAdapter(newArrayList)
        newRcv.adapter = adapter
        adapter.setOnItemClickListener(object : BookAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                //  Toast.makeText(this@MainActivity2, "You clicked on item no. $position ", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity2, ReadBookActivity::class.java)
                intent.putExtra("TitleTv", newArrayList[position].TitleTv)
                intent.putExtra("imageIv", newArrayList[position].imageIv)
                intent.putExtra("descriptionTv", newArrayList[position].descriptionTv)
                intent.putExtra("reads", reads[position])
                startActivity(intent)

            }

        })

        val clickme = findViewById<ImageButton>(R.id.logoutBtn);

        clickme.setOnClickListener {
            Toast.makeText(this, "Da Thoat Tai khoan.. ", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

}