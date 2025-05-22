package com.example.tasking.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasking.models.home.HomeViewModel

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        return HomeViewModel(context) as T
    }

}