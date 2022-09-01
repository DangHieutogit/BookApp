package com.example.bookapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.databinding.RowCategoryBinding
import com.google.firebase.database.FirebaseDatabase

class AdapterCategory:RecyclerView.Adapter<AdapterCategory.HolderCategory>,Filterable {

    /* phần category em chưa xây dựng xong vì hơi gấp nên em vẫn
    để lại để tiện sau này xây dựng tiếp ạ */

    private val context: Context
    public var categoryArrayList: ArrayList<ModelCategory>
    private var filterList: ArrayList<ModelCategory>

    private var filter : FilterCategory? = null

    private lateinit var binding: RowCategoryBinding
    
    //phần này em chưa xây dựng xong nên em vẫn để lưu lại ạ 



    constructor(context: Context, categoryArrayList: ArrayList<ModelCategory>) {
        this.context = context
        this.categoryArrayList = categoryArrayList
        this.filterList = categoryArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        //inflate bid row
        binding = RowCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderCategory(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {
//        get data set data handle click
        val model = categoryArrayList[position]
        val id = model.id
        val category = model.category
        val uid = model.uid
        val timestamp = model.timestamp

        //set data
        holder.categoryTv.text = category

        //handle click, delete category
        holder.deleteBtn.setOnClickListener {
            //confirm sau khi xoa
            val builder = AlertDialog.Builder(context)
            builder.setTitle("delete")
                .setMessage("Ban co chac chan muon xoa category")
                .setPositiveButton("Confirm"){a, d->
                    Toast.makeText(context,"Dang xoa..",Toast.LENGTH_SHORT).show()
                    deleteCategory(model, holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()

                }.show()

        }

    }

    private fun deleteCategory(model: ModelCategory, holder: HolderCategory) {
        //get id of category to delete
        val  id = model.id
        //fb Database > category
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context,"Deleted..",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e->
                Toast.makeText(context,"Unable to delete duo to ${e.message}..",Toast.LENGTH_SHORT).show()

            }
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }



    //viewholder
    inner class HolderCategory(itemview : View): RecyclerView.ViewHolder(itemview){
        //init ui views

        var categoryTv:TextView = binding.categoryTv
        var deleteBtn:ImageButton = binding.deleteBtn


    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterCategory(filterList, this)
        }
        return filter as FilterCategory
    }


}