package com.example.bookapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView


class BookAdapter(private val bookList: ArrayList<Book>): RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book,
        parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = bookList[position]
       holder.imageIv.setImageResource(currentItem.imageIv)
        holder.TitleTv.text = (currentItem.TitleTv)
        holder.descriptionTv.text = (currentItem.descriptionTv)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val imageIv : ShapeableImageView = itemView.findViewById(R.id.imageIv)
        val TitleTv: TextView = itemView.findViewById(R.id.TitleTv)
        val descriptionTv :TextView = itemView.findViewById(R.id.descriptionTv)

        init {
                itemView.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
        }

    }


}