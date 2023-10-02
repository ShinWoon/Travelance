package com.moneyminions.presentation.viewmodel.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.home.TravelRoomFriendDto
import com.moneyminions.domain.usecase.home.GetTravelRoomFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

private const val TAG = "싸피"
@HiltViewModel
class CardGameViewModel @Inject constructor(
    private val getTravelRoomFriendsUseCase: GetTravelRoomFriendsUseCase
): ViewModel() {
    private val _getTravelRoomFriendsState = MutableStateFlow<NetworkResult<List<TravelRoomFriendDto>>>(NetworkResult.Idle)
    val getTravelRoomFriendState = _getTravelRoomFriendsState.asStateFlow()

    fun getTravelRoomFriends(roomId: Int) = viewModelScope.launch {
        _getTravelRoomFriendsState.value = NetworkResult.Loading
        _getTravelRoomFriendsState.emit(getTravelRoomFriendsUseCase.invoke(roomId))
        Log.d(TAG, "getTravelRoomFriends: $getTravelRoomFriendState")
    }

    fun getWinner(friendsList: List<TravelRoomFriendDto>): TravelRoomFriendDto {
        val randomIndex = Random.nextInt(friendsList.size)
        return friendsList[randomIndex]
    }
}