package com.android_labs.criminal04room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CrimeListFragment : Fragment() {

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private val crimeViewModel: CrimeViewModel by lazy {
        ViewModelProvider(this)[CrimeViewModel::class.java]
    }

    private lateinit var crimeListView: RecyclerView

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
        this.crimeListView.adapter = CrimeListAdapter(emptyList())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.crimeViewModel.crimeList.observe(viewLifecycleOwner, Observer { crimes ->
            this.crimeListView.adapter = CrimeListAdapter(crimes)
        })
    }

    override fun onStart() {
        super.onStart()
    }

    private inner class CrimeListHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val crimeTitle = view.findViewById<TextView>(R.id.crime_title_field)
        private val crimeDate = view.findViewById<TextView>(R.id.crime_date_field)
        private val crimeIsSolved = view.findViewById<ImageView>(R.id.crime_solved_field)

        fun bind(crime: CrimeEntity) {
            this.crimeTitle.text = crime.title
            this.crimeDate.text = crime.date.toString()

            this.crimeIsSolved.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private inner class CrimeListAdapter(val crimeList: List<CrimeEntity>) :
        RecyclerView.Adapter<CrimeListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListHolder {
            return CrimeListHolder(
                layoutInflater.inflate(
                    R.layout.fragment_crime_list_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return crimeList.size
        }

        override fun onBindViewHolder(holder: CrimeListHolder, position: Int) {
            holder.bind(this.crimeList[position])
        }
    }
}