package com.example.noteapp.presentation.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.noteapp.data.local.model.Note
import com.example.noteapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val _resource = MutableStateFlow<Resource>(Resource.Loading)
    val resource: StateFlow<Resource> get() = _resource

    protected fun setLoading() {
        _resource.value = Resource.Loading
    }

    protected fun setSuccess(notes: List<Note>, note: Note? = null) {
        _resource.value = Resource.Success(notes, note)
    }

    protected fun setError(e: Throwable) {
        _resource.value = Resource.Error(e)
    }
}