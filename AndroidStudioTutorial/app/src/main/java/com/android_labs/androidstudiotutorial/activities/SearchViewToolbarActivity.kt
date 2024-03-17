package com.android_labs.androidstudiotutorial.activities

import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.android_labs.androidstudiotutorial.R

class SearchViewToolbarActivity : AppCompatActivity() {

    private lateinit var listView1: ListView

    private val names = listOf(
        "Christopher",
        "Jenny",
        "Maria",
        "steve",
        "Chris",
        "lvana",
        "Michael",
        "Craig",
        "Kelly",
        "Jospeh",
        "Christene",
        "Sergio",
        "Mubariz",
        "Mike",
        "Alex"
    )

    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view_toolbar)

        setSupportActionBar(findViewById(R.id.materialToolBar))

        // supportActionBar?.title = ""
        supportActionBar?.setDisplayShowTitleEnabled(false)

        this.arrayAdapter =
            ArrayAdapter(this@SearchViewToolbarActivity, android.R.layout.simple_list_item_1, names)

        this.listView1 = findViewById(R.id.listView1)
        this.listView1.adapter = arrayAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu3, menu)

        var menuItem = menu?.findItem(R.id.biSearch)

        var searchView: SearchView = menuItem?.actionView as SearchView

        searchView.queryHint = "Type here to search ..."

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                this@SearchViewToolbarActivity.arrayAdapter.filter.filter(newText)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}