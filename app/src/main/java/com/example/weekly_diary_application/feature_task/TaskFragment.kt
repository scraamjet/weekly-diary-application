
package com.example.weekly_diary_application.feature_task

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weekly_diary_application.databinding.FragmentTaskBinding
import com.example.weekly_diary_application.feature_calendar.DateConverter

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private lateinit var dateConverter: DateConverter
    private var taskName: String? = null
    private var taskDescription: String? = null
    private var taskDateStart: String? = null
    private var taskDateFinish: String? = null
    private var bundle: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(layoutInflater)

        return binding.root
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bundle = arguments
        dateConverter = DateConverter()

        taskDescription = bundle?.getString("task_description")
        taskName = bundle?.getString("task_name")
        taskDateStart = bundle?.getLong("task_date_start")
            ?.let { dateConverter.convertLongToTimeString(it) }
        taskDateFinish = bundle?.getLong("task_date_finish")
            ?.let{dateConverter.convertLongToTimeString(it)}



        binding.taskDescription.text = taskDescription
        binding.taskName.text = taskName

        binding.taskTime.text = "$taskDateStart - $taskDateFinish"


    }
}