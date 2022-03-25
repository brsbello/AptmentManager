package com.example.aptmentmanager.warnings.addwarning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aptmentmanager.R
import com.example.aptmentmanager.databinding.AddWarningFragmentBinding
import com.example.aptmentmanager.extensions.format
import com.example.aptmentmanager.extensions.text
import com.example.aptmentmanager.models.WarningModel
import com.example.aptmentmanager.warnings.data.WarningDataBase
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddWarningFragment : Fragment() {

    private lateinit var binding: AddWarningFragmentBinding
    private lateinit var viewModel: AddWarningViewModel
    private val db by lazy {
        activity?.let {
            WarningDataBase.intance(it.baseContext).WarningDao()
        }
    }
    private var idWarning: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddWarningFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddWarningViewModel::class.java]
        recoverData()
        insertListeners()

    }

    private fun recoverData() {
        val bundle = arguments
        if (bundle != null) {
            val result = bundle.getParcelable<WarningModel>("my_data")
            if (result != null) {
                binding.BTNewWarning.text = getString(R.string.alterar)
                idWarning = result.id
                binding.ETTitle.text = result.title
                binding.ETDate.text = result.date
                binding.ETHour.text = result.hour
                binding.ETDescription.text = result.description
            }
        }
    }

    private fun insertListeners() {
        configDate()
        configHour()
        configCancelButton()
        configCreateButton()
        configBackImg()
    }

    private fun configBackImg() {
        binding.toolbar.setOnClickListener {
            val controller = findNavController()
            controller.navigateUp()
        }
    }

    private fun configCreateButton() {
        binding.BTNewWarning.setOnClickListener {
            val warning = newWarning()
            if (idWarning > 0) {
                db?.update(warning)
            } else {
                db?.save(warning)
            }
            val controller = findNavController()
            controller.navigateUp()
        }
    }

    private fun newWarning(): WarningModel {
        return WarningModel(
            id = idWarning,
            title = binding.ETTitle.text,
            description = binding.ETDescription.text,
            date = binding.ETDate.text,
            hour = binding.ETHour.text

        )
    }

    private fun configCancelButton() {
        binding.BTCancel.setOnClickListener {
            val controller = findNavController()
            controller.navigateUp()
        }
    }

    private fun configHour() {
        binding.ETHour.editText?.setOnClickListener {
            val timePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.ETHour.text = "${hour}:${minute}"
            }
            activity?.let { it1 -> timePicker.show(it1.supportFragmentManager, "TIME_PICKER_TAG") }
        }
    }

    private fun configDate() {
        binding.ETDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.ETDate.text = Date(it + offset).format()
            }
            activity?.let { it1 -> datePicker.show(it1.supportFragmentManager, "DATE_PICKER_TAG") }
        }
    }
}