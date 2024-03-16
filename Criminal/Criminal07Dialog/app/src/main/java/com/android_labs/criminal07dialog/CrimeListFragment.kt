package com.android_labs.criminal07dialog

import android.content.Context
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
import java.util.UUID

class CrimeListFragment: Fragment() {

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

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
        this.crimeListView.adapter = CrimeListAdapter(emptyList())

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.crimeListViewModel.crimeList.observe(viewLifecycleOwner, Observer { crimes ->
            this.crimeListView.adapter = CrimeListAdapter(crimes)
        })
    }

    private inner class CrimeListHolder(val view: View): RecyclerView.ViewHolder (view),
        View.OnClickListener {

        private lateinit var currCrime: CrimeEntity

        private val crimeTitleField = view.findViewById<TextView>(R.id.crime_title_field)
        private val crimeDateField = view.findViewById<TextView>(R.id.crime_date_field)
        private val crimeIsSolvedField = view.findViewById<ImageView>(R.id.crime_is_solved_img)

        init {
            this.view.setOnClickListener(this)
        }

        fun bind(crime: CrimeEntity) {
            this.currCrime = crime

            this.crimeTitleField.text = crime.title
            this.crimeDateField.text = CrimeCreateFragment.toDateString(crime.date)

            this.crimeIsSolvedField.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(this.currCrime.id)
        }
    }

    private inner class CrimeListAdapter(val crimeList: List<CrimeEntity>): RecyclerView.Adapter<CrimeListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListHolder {
            return CrimeListHolder(layoutInflater.inflate(R.layout.fragment_crime_item, parent, false))
        }

        override fun getItemCount(): Int {
            return crimeList.size
        }

        override fun onBindViewHolder(holder: CrimeListHolder, position: Int) {
            holder.bind(this.crimeList[position])
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Callbacks) {
            this.callbacks = context as Callbacks
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.callbacks = null
    }
}