package com.example.weekly_diary_application.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.weekly_diary_application.R
import com.example.weekly_diary_application.network.TaskJSONConverter
import com.example.weekly_diary_application.data.TaskDatabaseFacade
import com.example.weekly_diary_application.databinding.ActivityLauncherBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LauncherActivity : AppCompatActivity(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job
    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //parseJSON()
    }
    private fun parseJSON(){
        val inputStream = assets.open("diary_data.json")
        val taskJSONConverter = TaskJSONConverter()
        val taskList = taskJSONConverter.readJsonToList(inputStream)
        println(taskList)
        val taskEntityList = taskJSONConverter.convertToTaskEntity(taskList)

        launch {
                TaskDatabaseFacade(application as Application).insertTaskJSON(taskEntityList)
        }
        inputStream.close()
    }
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment).navController
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}