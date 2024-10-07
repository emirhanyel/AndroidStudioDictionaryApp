package com.example.dictionaryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.RecentSearchItemBinding


class RecentSearchAdapter(var recentSearchList: ArrayList<String>):RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>() {

    inner class RecentSearchViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = RecentSearchItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_search_item,parent,false)
        return RecentSearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recentSearchList.size
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.binding.recentsearchTextView.text = recentSearchList[position]
    }

}