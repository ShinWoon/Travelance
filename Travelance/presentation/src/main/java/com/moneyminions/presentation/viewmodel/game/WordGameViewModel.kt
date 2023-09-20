package com.moneyminions.presentation.viewmodel.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class WordGameViewModel @Inject constructor(

): ViewModel() {
    val consonantList = listOf("ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ")


    private val _firstConsonant = mutableStateOf("")
    val firstConsonant: State<String> = _firstConsonant
    fun setFirstConsonant(){
        val randomIndex = Random.nextInt(consonantList.size)
        val randomConsonant = consonantList[randomIndex]
        _firstConsonant.value = randomConsonant
    }

    private val _secondConsonant = mutableStateOf("")
    val secondConsonant: State<String> = _secondConsonant
    fun setSecondConsonant(){
        val randomIndex = Random.nextInt(consonantList.size)
        val randomConsonant = consonantList[randomIndex]
        _secondConsonant.value = randomConsonant
    }

    private val _isShowWord = mutableStateOf(false)
    val isShowWord: State<Boolean> = _isShowWord
    fun setIsShowWord(value: Boolean){
        _isShowWord.value = value
    }

    private val _isStart = mutableStateOf(false)
    val isStart: State<Boolean> = _isStart
    fun setIsStart(value: Boolean){
        _isStart.value = value
    }

    private val _isLottieFinished = mutableStateOf(false)
    val isLottieFinished: State<Boolean> = _isLottieFinished
    fun setIsLottieFinished(value: Boolean){
        _isLottieFinished.value = value
    }
}