package com.moneyminions.presentation.viewmodel.announcement

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AnnouncementViewModel @Inject constructor(

) : ViewModel(){
    private val _title = mutableStateOf("")
    val title: State<String> = _title
    fun setTitle(title: String) {
        _title.value = title
    }
    
    
    private val _content = mutableStateOf("")
    val content: State<String> = _content
    fun setContent(content: String) {
        _content.value = content
    }
    
    private val _link = mutableStateOf("")
    val link: State<String> = _link
    fun setLink(link: String) {
        this._link.value = link
    }
}