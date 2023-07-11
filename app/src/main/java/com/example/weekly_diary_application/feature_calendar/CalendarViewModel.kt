package com.example.weekly_diary_application.feature_calendar

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.weekly_diary_application.common.Application
import com.example.weekly_diary_application.data.TaskDatabaseFacade
import com.example.weekly_diary_application.data.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(application: Application,
                        private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val _tasksList: MutableLiveData<List<TaskEntity>> = MutableLiveData()
    val tasksList: LiveData<List<TaskEntity>> = _tasksList

    val taskDbInteractor = TaskDatabaseFacade(application)

    companion object {
        fun provideFactory(
            myRepository: Application,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return CalendarViewModel(myRepository, handle) as T
                }
            }
    }

    fun getDateTasks(day: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = taskDbInteractor.getTasksForDay(day)
            launch(Dispatchers.Main) {
                _tasksList.value = tasks
            }
        }

    }
}