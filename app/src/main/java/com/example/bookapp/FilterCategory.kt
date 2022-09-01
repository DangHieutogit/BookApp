package com.example.bookapp

import android.widget.Filter


class FilterCategory: Filter {
    /* phần category em chưa xây dựng xong vì hơi gấp nên em vẫn
    để lại để tiện sau này xây dựng tiếp ạ */

    private var fiterList : ArrayList<ModelCategory>

    //adapter in which filter
    private var adapterCategory: AdapterCategory

    //contrustor
    constructor(fiterList: ArrayList<ModelCategory>, adapterCategory: AdapterCategory) {
        this.fiterList = fiterList
        this.adapterCategory = adapterCategory
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val result = FilterResults()

        //value should not be null
        if (constraint != null && constraint.isEmpty()){
            //searched value is nor null


            //change to upper case
            constraint = constraint.toString().uppercase()
            val filterModel: ArrayList<ModelCategory> = ArrayList()
            for (i in 0 until fiterList.size){
                //validate
                if (fiterList[i].category.uppercase().contains(constraint)){

                    //add to filter list
                    filterModel.add(fiterList[i])
                }
            }
            result.count = filterModel.size
            result.values = filterModel
        }
        else{
            //search value is either null or empty
            result.count = fiterList.size
            result.values = fiterList
        }

        return result//
    }

    override fun publishResults(constranit: CharSequence?, result: FilterResults) {
        //aply filter
        adapterCategory.categoryArrayList = result.values as ArrayList<ModelCategory> /* = java.util.ArrayList<com.example.bookapp.ModelCategory> */

        //notify
        adapterCategory.notifyDataSetChanged()
    }


}