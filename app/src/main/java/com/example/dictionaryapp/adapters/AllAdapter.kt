package com.example.dictionaryapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.adapters.word_adapter.MeaningAdapter
import com.example.dictionaryapp.data_part.Meaning
import com.example.dictionaryapp.data_part.Synonym
import com.example.dictionaryapp.data_part.Word
import com.example.dictionaryapp.databinding.Part1Binding
import com.example.dictionaryapp.databinding.Part3Binding
import com.example.dictionaryapp.databinding.WordMeaningItemBinding


class AllAdapter(val word: Word, val synonymList: List<Synonym>, var meaningList: ArrayList<Meaning>, private val itemClickInterface: ItemClickInterface, var buttonChangeList: ArrayList<String>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    val SHOW_WORD = 0
    val SHOW_MEANINGS = 1
    val SHOW_SYNONYMS = 2

    private lateinit var mediaPlayer: MediaPlayer


    inner class ShowWordViewHolder(private val binding: Part1Binding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.NounBtn.setOnClickListener {
                if(buttonChangeList.size>=2) {
                    binding.NounBtn.setBackgroundResource(R.drawable.change_button_border_color)
                    binding.CloseBtn.visibility = View.VISIBLE
                }
                itemClickInterface.showNoun(adapterPosition)

            }
            binding.VerbBtn.setOnClickListener {
                if(buttonChangeList.size>=2) {
                    binding.VerbBtn.setBackgroundResource(R.drawable.change_button_border_color)
                    binding.CloseBtn.visibility = View.VISIBLE
                }
                itemClickInterface.showVerb(adapterPosition)
            }
            binding.AdjectiveBtn.setOnClickListener {
                if(buttonChangeList.size>=2) {
                    binding.AdjectiveBtn.setBackgroundResource(R.drawable.change_button_border_color)
                    binding.CloseBtn.visibility = View.VISIBLE
                }
                itemClickInterface.showAdjective(adapterPosition)
            }
            binding.CloseBtn.setOnClickListener {
                binding.CloseBtn.visibility = View.GONE

                for(b in buttonChangeList){
                    if(b=="noun"){
                        binding.NounBtn.setBackgroundResource(R.drawable.background_three_buttons)
                        binding.NounBtn.setText("Noun")
                        binding.NounBtn.visibility = View.VISIBLE
                    }
                    else if(b=="verb"){
                        binding.VerbBtn.setBackgroundResource(R.drawable.background_three_buttons)
                        binding.VerbBtn.setText("Verb")
                        binding.VerbBtn.visibility = View.VISIBLE
                    }
                    else{
                        binding.AdjectiveBtn.setBackgroundResource(R.drawable.background_three_buttons)
                        binding.AdjectiveBtn.setText("Adjective")
                        binding.AdjectiveBtn.visibility = View.VISIBLE
                    }
                }
                itemClickInterface.clickClose(adapterPosition)
            }
        }


        fun bindShowWordView() {
            binding.WordText.text=word.word.capitalize()

            for(phoneticText in word.phonetics){
                if(phoneticText.text!=""){
                    binding.WordDifferentText.text=phoneticText.text
                    break
                }
            }

            if (binding.CloseBtn.visibility == View.VISIBLE) {
                if (buttonChangeList[0] == "noun") {
                    if (buttonChangeList.size == 3) {
                        binding.VerbBtn.visibility = View.GONE
                        binding.AdjectiveBtn.visibility = View.GONE
                        if (buttonChangeList[1] == "verb") {
                            binding.NounBtn.setText("Noun, Verb, Adjective")
                        } else {
                            binding.NounBtn.setText("Noun, Adjective, Verb")
                        }
                    } else if (buttonChangeList.size == 2) {
                        if (buttonChangeList[1] == "verb") {
                            binding.VerbBtn.visibility = View.GONE
                            binding.NounBtn.setText("Noun, Verb")
                        } else {
                            binding.AdjectiveBtn.visibility = View.GONE
                            binding.NounBtn.setText("Noun, Adjective")
                        }
                    } else {
                        binding.NounBtn.setText("Noun")
                    }
                } else if (buttonChangeList[0] == "verb") {
                    if (buttonChangeList.size == 3) {
                        binding.NounBtn.visibility = View.GONE
                        binding.AdjectiveBtn.visibility = View.GONE
                        if (buttonChangeList[1] == "noun") {
                            binding.VerbBtn.setText("Verb, Noun, Adjective")
                        } else {
                            binding.VerbBtn.setText("Verb, Adjective, Noun")
                        }
                    } else if (buttonChangeList.size == 2) {
                        if (buttonChangeList[1] == "noun") {
                            binding.NounBtn.visibility = View.GONE
                            binding.VerbBtn.setText("Verb, Noun")
                        } else {
                            binding.AdjectiveBtn.visibility = View.GONE
                            binding.VerbBtn.setText("Verb, Adjective")
                        }
                    } else {
                        binding.VerbBtn.setText("Verb")
                    }
                } else {
                    if (buttonChangeList.size == 3) {
                        binding.VerbBtn.visibility = View.GONE
                        binding.NounBtn.visibility = View.GONE
                        if (buttonChangeList[1] == "verb") {
                            binding.AdjectiveBtn.setText("Adjective, Verb, Noun")
                        } else {
                            binding.AdjectiveBtn.setText("Adjective, Noun, Verb")
                        }
                    } else if (buttonChangeList.size == 2) {
                        if (buttonChangeList[1] == "noun") {
                            binding.NounBtn.visibility = View.GONE
                            binding.AdjectiveBtn.setText("Adjective, Noun")
                        } else {
                            binding.VerbBtn.visibility = View.GONE
                            binding.AdjectiveBtn.setText("Adjective, Verb")
                        }
                    } else {
                        binding.AdjectiveBtn.setText("Adjective")
                    }
                }
            }
            else{
                for(b in buttonChangeList) {
                    if (b == "noun") {

                        binding.NounBtn.visibility = View.VISIBLE
                    } else if (b == "verb") {

                        binding.VerbBtn.visibility = View.VISIBLE
                    } else {

                        binding.AdjectiveBtn.visibility = View.VISIBLE
                    }
                }
            }



            binding.buttonSound.setOnClickListener {
                for (phonetic in word.phonetics) {
                    if(phonetic.audio!=""){
                        playAudioFromUrl(phonetic.audio)
                        break
                    }
                }

            }

        }

        private fun playAudioFromUrl(url: String) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener {
                    it.start()
                }
                setOnErrorListener { mp, what, extra ->

                    true
                }
                setOnCompletionListener {
                    it.release()
                }
                prepareAsync()
            }
        }

    }

    inner class ShowMeaningsViewHolder(private val binding: WordMeaningItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.recyclerViewMeaningItem.setHasFixedSize(true)
            binding.recyclerViewMeaningItem.layoutManager =
                LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        }

        fun bindShowMeaningsView(meaningList: List<Meaning>){
            val adapter = MeaningAdapter(meaningList)
            binding.recyclerViewMeaningItem.adapter = adapter
        }
    }

    inner class ShowSynonymsViewHolder(private val binding: Part3Binding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.recyclerSynonym.setHasFixedSize(true)
            /*binding.recyclerSynonym.layoutManager =
                LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)*/
            binding.recyclerSynonym.layoutManager = GridLayoutManager(binding.root.context, 4)
        }

        fun bindShowSynonymsView(){
            val adapter = SynonymAdapter(synonymList)
            binding.recyclerSynonym.adapter = adapter
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            SHOW_MEANINGS ->
                R.layout.word_meaning_item
            SHOW_WORD ->
                R.layout.part1
            else ->
                R.layout.part3
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.word_meaning_item -> {
                val binding = WordMeaningItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ShowMeaningsViewHolder(binding)
            }
            R.layout.part1 -> {
                val binding = Part1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                ShowWordViewHolder(binding)
            }
            else -> {
                val binding = Part3Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                ShowSynonymsViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
                is ShowMeaningsViewHolder -> {
                    (holder as ShowMeaningsViewHolder).bindShowMeaningsView(meaningList)
                }
                is ShowWordViewHolder -> {
                    holder.bindShowWordView()
                }
                else ->{
                    (holder as ShowSynonymsViewHolder).bindShowSynonymsView()
                }
        }
    }

    interface ItemClickInterface {
        fun showNoun(position: Int)
        fun showVerb(position: Int)
        fun showAdjective(position: Int)
        fun clickClose(position: Int)
    }

    fun update(list : List<Meaning>, list2 : List<String>){
        buttonChangeList = ArrayList()
        buttonChangeList.addAll(list2)
        meaningList = ArrayList()
        meaningList.addAll(list)
        notifyDataSetChanged()
    }

}