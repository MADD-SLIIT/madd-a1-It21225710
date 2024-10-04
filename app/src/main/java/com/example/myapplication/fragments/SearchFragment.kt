package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.myapplication.CityClick1
import com.example.myapplication.CityClick2
import com.example.myapplication.R


class SearchFragment : Fragment() {

    lateinit var nineArch :Button
    lateinit var Thalawakele :Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_search, container, false)

        nineArch = view.findViewById(R.id.crd1txt1btn)
        Thalawakele = view.findViewById(R.id.crd2txt1btn)

        nineArch.setOnClickListener {
            val intent = Intent(activity, CityClick1::class.java)
            startActivity(intent)
        }

        Thalawakele.setOnClickListener {
            val intent = Intent(activity, CityClick2::class.java)
            startActivity(intent)
        }



        return view
    }
}