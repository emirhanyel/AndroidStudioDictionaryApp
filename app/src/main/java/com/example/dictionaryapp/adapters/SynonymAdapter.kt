package com.example.dictionaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.activities.SearchDetailActivity
import com.example.dictionaryapp.data_part.Synonym
import com.example.dictionaryapp.databinding.SynonymItemBinding


class SynonymAdapter(private val synonymList: List<Synonym>):
    RecyclerView.Adapter<SynonymAdapter.SynonymViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SynonymViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.synonym_item,parent,false)
        context = parent.context
        return SynonymViewHolder(view)
    }

    override fun getItemCount(): Int {
        return synonymList.size
    }

    override fun onBindViewHolder(holder: SynonymViewHolder, position: Int) {
        holder.binding.apply {
            synonymBtns.setText(synonymList[position].word)

            synonymBtns.setOnClickListener{
                val intent = Intent(this@SynonymAdapter.context, SearchDetailActivity::class.java).apply {
                    putExtra("DataFromMainToSearchDetailActivity",synonymList[position].word)
                }
                context.startActivity(intent)
            }
        }
    }

    inner class SynonymViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = SynonymItemBinding.bind(view)
    }

}