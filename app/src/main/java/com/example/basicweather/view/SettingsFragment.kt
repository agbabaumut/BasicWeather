package com.example.basicweather.view

import com.example.basicweather.databinding.FragmentsSettingsBinding
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class SettingsFragment : DialogFragment() {

    private lateinit var binding: FragmentsSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentsSettingsBinding.inflate(inflater, container, false)
        setupButtons()
        return binding.root
    }

    private fun setupButtons() {
        binding.btn1Hour.setOnClickListener {
            selectTimeInterval(1)
        }

        binding.btn6Hour.setOnClickListener {
            selectTimeInterval(6)
        }

        binding.btn24Hour.setOnClickListener {
            selectTimeInterval(24)
        }
    }

    private fun selectTimeInterval(selectedInterval: Int) {
        binding.btn1Hour.setTypeface(null, Typeface.NORMAL)
        binding.btn6Hour.setTypeface(null, Typeface.NORMAL)
        binding.btn24Hour.setTypeface(null, Typeface.NORMAL)

        when (selectedInterval) {
            1 -> binding.btn1Hour.setTypeface(null, Typeface.BOLD)
            6 -> binding.btn6Hour.setTypeface(null, Typeface.BOLD)
            24 -> binding.btn24Hour.setTypeface(null, Typeface.BOLD)
        }

        dismiss()
    }
}
