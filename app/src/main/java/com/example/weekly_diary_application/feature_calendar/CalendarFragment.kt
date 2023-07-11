package com.example.weekly_diary_application.feature_calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weekly_diary_application.common.Application
import com.example.weekly_diary_application.R
import com.example.weekly_diary_application.data.TaskEntity
import com.example.weekly_diary_application.databinding.FragmentCalendarBinding
import java.time.Instant
import java.util.*


class CalendarFragment: Fragment() {

    private val viewModel: CalendarViewModel by viewModels {
        CalendarViewModel.provideFactory(
            (activity?.application as Application),
            this
        )
    }
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var bundle:Bundle
    private var hoursTaskMap =  mutableMapOf<Int, TaskEntity>()

    override fun onResume() {
        super.onResume()
        val time = Calendar.getInstance(TimeZone.getDefault())
        changeDate(time.get(Calendar.YEAR),time.get(Calendar.MONTH),time.get(Calendar.DAY_OF_MONTH))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        val times = resources.getStringArray(R.array.base_time)
        context?.let {
            binding.listViewTasks.adapter =
             ArrayAdapter(it, R.layout.task_item, R.id.task_time,times) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tasksList.observe(viewLifecycleOwner) {
            onTaskListUpd(it)
        }
        binding.calendarView
            .setOnDateChangeListener { _, year, month, dayOfMonth ->
               changeDate(year, month, dayOfMonth)
            }

    }
    private fun changeDate( year: Int, month: Int, dayOfMonth: Int){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        viewModel.getDateTasks(calendar.timeInMillis)

        val date = (dayOfMonth.toString() + "-"
            + (month + 1) + "-" + year)
        binding.idTVDate.text = date
    }
    private fun onTaskListUpd(tasks: List<TaskEntity>){
        context?.let { it ->
            hoursTaskMap = mutableMapOf<Int, TaskEntity>()
            tasks.forEach {
                val cal: Calendar = Calendar.getInstance()
                cal.timeInMillis = it.date_start
                cal.timeZone = TimeZone.getDefault();
                hoursTaskMap[cal.get(Calendar.HOUR_OF_DAY)] = it
            }
            val times = resources.getStringArray(R.array.base_time)
            hoursTaskMap.forEach {
                times[it.key] = it.value.name + " " +Instant.ofEpochSecond(it.value.date_start)
            }
            val adapter =
                ArrayAdapter(it, R.layout.task_item, R.id.task_time,times)
            binding.listViewTasks.adapter = adapter
            binding.listViewTasks.setOnItemClickListener { adapterView, view, i, l ->
                toTask(i)
            }
        }
    }
    private fun toTask(pos: Int){
       val task = hoursTaskMap[pos]
        bundle = Bundle()
        if(task != null ){
            bundle.putString("task_name", task.name)
            bundle.putString("task_description", task.description)
            bundle.putLong("task_date_start", task.date_start)
            bundle.putLong("task_date_finish", task.date_finish)
            findNavController().navigate(R.id.action_calendarFragment_to_taskFragment,bundle)
        }
    }
}