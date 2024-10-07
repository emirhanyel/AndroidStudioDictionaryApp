package com.example.dictionaryapp.adapters.word_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data_part.Meaning
import com.example.dictionaryapp.databinding.WordMeaningItemBinding


class MeaningAdapter(private var wordMeanings: List<Meaning>) :
    RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningAdapter.MeaningViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_meaning_item,parent,false)
        context = parent.context
        return MeaningViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeaningAdapter.MeaningViewHolder, position: Int) {
        holder.binding.apply {
            val definitionAdapter = DefinitionAdapter(wordMeanings[position].definitions,wordMeanings[position].partOfSpeech)
            recyclerViewMeaningItem.adapter=definitionAdapter
            recyclerViewMeaningItem.layoutManager=
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun getItemCount(): Int {
        return wordMeanings.size
    }

    class MeaningViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = WordMeaningItemBinding.bind(view)
    }

}