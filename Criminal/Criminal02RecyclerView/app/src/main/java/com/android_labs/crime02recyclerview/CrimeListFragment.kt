package com.android_labs.crime02recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CrimeListFragment: Fragment() {

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private lateinit var crimeListView: RecyclerView

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        this.crimeListView = view.findViewById(R.id.crime_list_view)
        this.crimeListView.layoutManager = LinearLayoutManager(context)

        this.crimeListView.adapter = CrimeListAdapter(this.crimeListViewModel.crimeList)

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    private inner class CrimeListItemHolder(var view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var crimeItem: CrimeDataModel

        private var crimeTitleField = view.findViewById<TextView>(R.id.crime_title_field)
        private var crimeDateField = view.findViewById<TextView>(R.id.crime_date_field)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: CrimeDataModel) {
            this.crimeItem = crime
            this.crimeTitleField.text = crime.title
            this.crimeDateField.text = crime.date.toString()
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, this.crimeItem.title + " pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimeListAdapter(val crimeList: List<CrimeDataModel>): RecyclerView.Adapter<CrimeListItemHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListItemHolder {
            val view = layoutInflater.inflate(R.layout.fragment_crime_item, parent, false)
            return CrimeListItemHolder(view)
        }

        override fun getItemCount(): Int {
            return crimeList.size
        }

        override fun onBindViewHolder(holder: CrimeListItemHolder, position: Int) {
            holder.bind(crimeList[position])
        }
    }
}