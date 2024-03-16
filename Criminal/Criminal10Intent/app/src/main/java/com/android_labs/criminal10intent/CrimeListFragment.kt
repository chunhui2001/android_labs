package com.android_labs.criminal10intent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
        fun onCrimeSelected(uuid: UUID)
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private var callbacks: Callbacks? = null

    private lateinit var crimeListView: RecyclerView

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        this.callbacks = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.crimeListViewModel.crimeList.observe(viewLifecycleOwner, Observer { crimes ->
            this.crimeListView.adapter = CrimeListAdapter(crimes)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_crime -> {
                var crime = CrimeEntity()
                callbacks?.onCrimeSelected(crime.id)
                return true
            } else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private inner class CrimeListHolder(var view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var currCrime: CrimeEntity

        private var crimeTitleField = view.findViewById<TextView>(R.id.crime_title_field)
        private var crimeDateField = view.findViewById<TextView>(R.id.crime_date_field)
        private var crimeIsSolvedField = view.findViewById<ImageView>(R.id.crime_is_solved_img)

        init {
            view.setOnClickListener(this)
        }

        fun bind(crime: CrimeEntity) {
            this.currCrime = crime

            this.crimeTitleField.text = crime.title
            this.crimeDateField.text = crime.date.toString()

            this.crimeIsSolvedField.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(currCrime.id)
        }
    }

    private inner class CrimeListAdapter(val crimeList: List<CrimeEntity>): RecyclerView.Adapter<CrimeListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListHolder {
            return CrimeListHolder(layoutInflater.inflate(R.layout.fragment_crime_item, parent, false))
        }

        override fun getItemCount(): Int {
            return this.crimeList.size
        }

        override fun onBindViewHolder(holder: CrimeListHolder, position: Int) {
            holder.bind(this.crimeList[position])
        }
    }
}