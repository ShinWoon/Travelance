package com.moneyminions.presentation.viewmodel.example

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.example.ExampleDto
import com.moneyminions.domain.usecase.example.ExampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val ExampleUseCase: ExampleUseCase
): ViewModel() {

    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName
    fun setUserName(name: String){
        _userName.value = name
    }

    private val _exampleRepos = MutableStateFlow<List<ExampleDto>>(listOf())
    val exampleRepos: StateFlow<List<ExampleDto>> = _exampleRepos
    fun getUserRepos(user: String) = viewModelScope.launch {
        _exampleRepos.emit(ExampleUseCase.invoke(user))
    }
}