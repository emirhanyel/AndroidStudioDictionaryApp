package com.example.dictionaryapp.adapters.word_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data_part.Definition
import com.example.dictionaryapp.databinding.WordDefinitionItemBinding


class DefinitionAdapter(private val wordDefinitions: List<Definition>, private val partOfSpeech: String):
    RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionAdapter.DefinitionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_definition_item,parent,false)
        return DefinitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefinitionAdapter.DefinitionViewHolder, position: Int) {
        holder.binding.apply {
            textViewNumber.text="${position+1} -"
            textViewDefinition.text=wordDefinitions[position].definition
            textViewType.text=partOfSpeech.capitalize()

            if(wordDefinitions[position].example!=null){
                textViewExample.visibility = View.VISIBLE
                textViewExampleGiven.visibility = View.VISIBLE
                textViewExampleGiven.text=wordDefinitions[position].example
            }
        }
    }

    override fun getItemCount(): Int {
        return wordDefinitions.size
    }

    inner class DefinitionViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = WordDefinitionItemBinding.bind(view)
    }

}