package com.example.dictionaryapp.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.adapters.AllAdapter
import com.example.dictionaryapp.data_part.Meaning
import com.example.dictionaryapp.databinding.SearchdetailBinding
import com.example.dictionaryapp.data_part.Synonym
import com.example.dictionaryapp.data_part.Word
import com.example.dictionaryapp.retrofit_part.ApiClient
import com.example.dictionaryapp.retrofit_part.ApiService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchDetailActivity: AppCompatActivity(), AllAdapter.ItemClickInterface {

    private lateinit var binding: SearchdetailBinding
    lateinit var wordTakenFromMainActivity: String
    lateinit var wordService: ApiService
    lateinit var synonymService: ApiService
    var synonymResponse: List<Synonym>? = null
    var wordResponse: List<Word>? = null
    lateinit var BtnBack : Button
    var filteredNounResponse: ArrayList<Meaning> = ArrayList()
    var filteredVerbResponse: ArrayList<Meaning> = ArrayList()
    var filteredAdjectiveResponse: ArrayList<Meaning> = ArrayList()
    var filteredMultipleResponse: ArrayList<Meaning> = ArrayList()
    var filteredAllResponse: ArrayList<Meaning> = ArrayList()
    lateinit var allAdapter: AllAdapter
    var buttonChange: ArrayList<String> = ArrayList()
    var buttonChangeAll: ArrayList<String> = ArrayList()
    var newWordResponse: ArrayList<Word> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchdetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        BtnBack = binding.Backbutton
        wordTakenFromMainActivity =
            intent.getStringExtra("DataFromMainToSearchDetailActivity").toString()

        binding.recyclerAll.setHasFixedSize(true)
        binding.recyclerAll.layoutManager = LinearLayoutManager(this@SearchDetailActivity)

        wordService = ApiClient.getClientWord().create(ApiService::class.java)
        wordProcess(wordTakenFromMainActivity)

        BtnBack.setOnClickListener {

            val intent = Intent(this@SearchDetailActivity, MainActivity::class.java)
            startActivity(intent)
        }


        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    private fun wordProcess(word: String) {
        var requestWord = wordService.getDefinitions(wordTakenFromMainActivity)
        requestWord.enqueue(object : Callback<List<Word>> {

            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
                if (response.isSuccessful) {
                    wordResponse = response.body()
                    if (wordResponse != null) {
                        synonymService = ApiClient.getClientSynonym().create(ApiService::class.java)
                        synonymProcess(wordTakenFromMainActivity)
                    }
                } else {
                    Log.e("SearchDetailActivity", "Error: ${response.code()} ${response.message()}")
                    Toast.makeText(this@SearchDetailActivity, "Word Not Found!", Toast.LENGTH_LONG).show()
                    val intentt = Intent(this@SearchDetailActivity, MainActivity::class.java)
                    startActivity(intentt)
                }
            }

            override fun onFailure(call: Call<List<Word>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
                println(t.message.toString())
            }
        })
    }

    private fun synonymProcess(word: String) {
        var requestSynonym = synonymService.getSynonyms(wordTakenFromMainActivity)
        requestSynonym.enqueue(object : Callback<List<Synonym>> {

            override fun onResponse(
                call: Call<List<Synonym>>,
                response: Response<List<Synonym>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        synonymResponse = response.body()!!.take(5)

                        wordResponse!!.groupBy {
                            it.word
                        }.map {
                                entry ->
                            val wordGet = entry.key
                            val combinePhonetics = entry.value.flatMap { it.phonetics}
                            val combineMeanings = entry.value.flatMap { it.meanings }
                            newWordResponse.add(Word(wordGet,combinePhonetics,combineMeanings))
                        }

                        newWordResponse[0].meanings.filter {
                            it.partOfSpeech=="noun"
                        }.groupBy {
                            it.partOfSpeech
                        }.map {
                                entry ->
                            val partOfSpeech = entry.key
                            val combineDefs = entry.value.flatMap { it.definitions }
                            filteredNounResponse.add(Meaning(partOfSpeech,combineDefs))
                        }

                        newWordResponse[0].meanings.filter {
                            it.partOfSpeech=="verb"
                        }.groupBy {
                            it.partOfSpeech
                        }.map {
                                entry ->
                            val partOfSpeech = entry.key
                            val combineDefs = entry.value.flatMap { it.definitions }
                            filteredVerbResponse.add(Meaning(partOfSpeech,combineDefs))
                        }

                        newWordResponse[0].meanings.filter {
                            it.partOfSpeech=="adjective"
                        }.groupBy {
                            it.partOfSpeech
                        }.map {
                                entry ->
                            val partOfSpeech = entry.key
                            val combineDefs = entry.value.flatMap { it.definitions }
                            filteredAdjectiveResponse.add(Meaning(partOfSpeech,combineDefs))
                        }

                        if(filteredNounResponse.isNotEmpty()) {
                            filteredAllResponse.addAll(filteredNounResponse)
                            buttonChangeAll.add("noun")
                        }
                        if(filteredVerbResponse.isNotEmpty()) {
                            filteredAllResponse.addAll(filteredVerbResponse)
                            buttonChangeAll.add("verb")
                        }
                        if(filteredAdjectiveResponse.isNotEmpty()) {
                            filteredAllResponse.addAll(filteredAdjectiveResponse)
                            buttonChangeAll.add("adjective")
                        }

                        allAdapter =
                            AllAdapter(newWordResponse[0], synonymResponse!!, filteredAllResponse, this@SearchDetailActivity, buttonChangeAll)
                        binding.recyclerAll.adapter = allAdapter
                    }

                } else {
                    Log.e("SearchDetailActivity", "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Synonym>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun showNoun(position: Int) {
        if(!buttonChange.contains("noun")) {
            buttonChange.add("noun")
            filteredMultipleResponse.addAll(filteredNounResponse)
            allAdapter.update(filteredMultipleResponse, buttonChange)
        }
    }

    override fun showVerb(position: Int) {
        if(!buttonChange.contains("verb")) {
            buttonChange.add("verb")
            filteredMultipleResponse.addAll(filteredVerbResponse)
            allAdapter.update(filteredMultipleResponse, buttonChange)
        }
    }

    override fun showAdjective(position: Int) {
        if(!buttonChange.contains("adjective")) {
            buttonChange.add("adjective")
            filteredMultipleResponse.addAll(filteredAdjectiveResponse)
            allAdapter.update(filteredMultipleResponse, buttonChange)
        }
    }

    override fun clickClose(position: Int) {
        buttonChange.clear()
        filteredMultipleResponse.clear()
        allAdapter.update(filteredAllResponse, buttonChangeAll)
    }

}