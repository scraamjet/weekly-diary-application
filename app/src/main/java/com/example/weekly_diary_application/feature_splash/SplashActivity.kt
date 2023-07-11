package com.example.weekly_diary_application.feature_splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.weekly_diary_application.common.Application
import com.example.weekly_diary_application.network.TaskJSONConverter
import com.example.weekly_diary_application.common.LauncherActivity
import com.example.weekly_diary_application.data.TaskDatabaseFacade
import com.example.weekly_diary_application.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(),CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseJSON()
        Handler().postDelayed({
            val intent = Intent(this, LauncherActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
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
}