package com.example.dictionaryapp.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.adapters.RecentSearchAdapter
import com.example.dictionaryapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var searchButton : Button
    lateinit var searchEditText: EditText
    lateinit var wordFromEditText: String
    lateinit var recentSearchAdapter: RecentSearchAdapter
    var recentSearchList: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        searchButton=binding.ButtonSearch
        searchEditText=binding.SearchBar

        binding.recyclerRecentSearch.layoutManager = LinearLayoutManager(this)

        recentSearchList.add("Aarhus")
        recentSearchList.add("Abidjan")
        recentSearchList.add("Abu Dhabi")
        recentSearchList.add("Abuja")
        recentSearchList.add("Accra")

        recentSearchAdapter = RecentSearchAdapter(recentSearchList)
        binding.recyclerRecentSearch.adapter = recentSearchAdapter

        searchButton.setOnClickListener {
            wordFromEditText = searchEditText.text.toString()

            if(wordFromEditText!=""){
                val intent = Intent(this@MainActivity, SearchDetailActivity::class.java).apply {
                    putExtra("DataFromMainToSearchDetailActivity",wordFromEditText)
                }
                startActivity(intent)}
        }

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {*/
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}