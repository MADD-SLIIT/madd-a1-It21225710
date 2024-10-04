package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.myapplication.MyProfile
import com.example.myapplication.R
import com.example.myapplication.SignIn


class MenuFragment : Fragment() {

    lateinit var profile_btn : Button
    lateinit var logout_btn : Button
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_menu, container, false)

        // Initialize sharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)

        profile_btn = view.findViewById(R.id.myprofilebtn)
        logout_btn = view.findViewById(R.id.logoutbtn)

        profile_btn.setOnClickListener{
            profile_nav()
        }

        logout_btn.setOnClickListener{
            logOut()
        }

        return view
    }


    private fun profile_nav() {
        val intent = Intent(activity, MyProfile::class.java)
        startActivity(intent)
    }

    private fun logOut() {
        // Clear the SharedPreferences (or user session data)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Redirect to SignIn activity
        val intent = Intent(activity, SignIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}