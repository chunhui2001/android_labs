package com.android_labs.criminal06update

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.UUID

private const val ARG_CRIME_ID = "com.android_labs.criminal06update::crime_id"

class CrimeCreateFragment: Fragment() {

    companion object {
        fun newInstance(crimeId: UUID?): CrimeCreateFragment {
            return CrimeCreateFragment().apply {
                arguments = crimeId?.let {
                    Bundle().apply {
                        putSerializable(ARG_CRIME_ID, it)
                    }
                }
            }
        }
    }

    private lateinit var crimeTitleFieldEt: EditText
    private lateinit var crimeDateFieldBtn: Button
    private lateinit var crimeIsSolvedCbx: CheckBox

    private val crimeDetailsViewModel: CrimeDetailsViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID

        crimeId?.let {
            crimeDetailsViewModel.loadCrime(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_create, container, false)

        this.crimeTitleFieldEt = view.findViewById(R.id.crime_title_et)
        this.crimeDateFieldBtn = view.findViewById(R.id.crime_date_btn)
        this.crimeIsSolvedCbx = view.findViewById(R.id.crime_is_solved_cbx)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crimeDetailsViewModel.crimeLiveData.observe(viewLifecycleOwner, Observer { crime ->
            crime?.let {
                this.crimeTitleFieldEt.setText(it.title)
                this.crimeDateFieldBtn.text = it.date.toString()
                this.crimeIsSolvedCbx.isChecked = it.isSolved
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onStop() {
        super.onStop()
        val v1 = this.crimeTitleFieldEt.text.toString()
        var v2 = this.crimeDateFieldBtn.text.toString()
        var v3 = this.crimeIsSolvedCbx.isChecked
        crimeDetailsViewModel.saveCrime(v1, v2, v3)
    }
}