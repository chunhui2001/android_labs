package com.android_labs.criminal03constraintlayout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

    private inner class CrimeListHolder(var view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var crimeItem: CrimeDataModel

        private val crimeTitleField = view.findViewById<TextView>(R.id.crime_title_field)
        private val crimeDateField = view.findViewById<TextView>(R.id.crime_date_field)
        private val crimeSolvedField = view.findViewById<ImageView>(R.id.crime_solved_field)

        init {
            view.setOnClickListener(this)
        }

        fun bind(currCrime: CrimeDataModel) {
            this.crimeItem = currCrime
            this.crimeTitleField.text = currCrime.title
            this.crimeDateField.text = currCrime.date.toString()

            crimeSolvedField.visibility = if (currCrime.solved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(view: View?) {
            Toast.makeText(context, this.crimeItem.title + " pressed", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimeListAdapter(var crimeList: List<CrimeDataModel>): RecyclerView.Adapter<CrimeListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListHolder {
            val view = layoutInflater.inflate(R.layout.fragment_crime_item, parent, false)
            return CrimeListHolder(view)
        }

        override fun getItemCount(): Int {
            return crimeList.size
        }

        override fun onBindViewHolder(holder: CrimeListHolder, position: Int) {
            holder.bind(crimeList[position])
        }
    }

}