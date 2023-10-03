package com.moneyminions.presentation.viewmodel.travellist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.travellist.CreateTravelRoomDto
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.model.travellist.TravelUserDto
import com.moneyminions.domain.usecase.traveledit.RequestJoinUserUseCase
import com.moneyminions.domain.usecase.travellist.CreateTravelRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

private const val TAG = "CreateTravelViewModel_D210"

@HiltViewModel
class CreateTravelViewModel @Inject constructor(
    private val createTravelRoomUseCase: CreateTravelRoomUseCase,
    private val requestJoinUserUseCase: RequestJoinUserUseCase,
) : ViewModel() {

    private val _travelName = mutableStateOf("")
    val travelName: State<String> = _travelName
    fun setTravelName(name: String) {
        _travelName.value = name
    }

    private val _travelBudget = mutableStateOf("")
    val travelBudget: State<String> = _travelBudget
    fun setTravelBudget(budget: String) {
        _travelBudget.value = budget
    }

    private val _startDate = mutableStateOf("")
    val startDate: State<String> = _startDate
    fun setStartDate(date: String) {
        _startDate.value = date
    }

    private val _endDate = mutableStateOf("")
    val endDate: State<String> = _endDate
    fun setEndDate(date: String) {
        _endDate.value = date
    }

    private val _nickName = mutableStateOf("")
    val nickName: State<String> = _nickName
    fun setNickName(name: String) {
        _nickName.value = name
    }

    private val _profileImage = mutableStateOf("")
    val profileImage: State<String> = _profileImage
    fun setProfileImage(image: String) {
        Log.d(TAG, "setProfileImage: $image")
        _profileImage.value = image
    }

    // 여행방 입력 check
    fun InputTravelCheck(): Boolean {
        return _travelName.value.isNullOrEmpty() || _travelBudget.value.isNullOrEmpty() || _startDate.value.isNullOrEmpty() || _endDate.value.isNullOrEmpty()
    }

    // 프로필 등록 입력 check
    fun InputProfileCheck(): Boolean {
        Log.d(TAG, "InputProfileCheck: ${_nickName.value} / ${_profileImage.value}")
        return _nickName.value.isNullOrEmpty() || _profileImage.value.isNullOrEmpty()
    }

    /**
     * 여행방 생성
     */
    private val _createTravelRoomResult =
        MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val createTravelRoomResult = _createTravelRoomResult.asStateFlow()
    fun createTravelRoom(imageFiles: MultipartBody.Part?) {
        viewModelScope.launch {
            Log.d(TAG, "createTravelRoom: $imageFiles")
            _createTravelRoomResult.emit(
                createTravelRoomUseCase.invoke(
                    CreateTravelRoomDto(
                        travelUserInfo = TravelUserDto(
                            nickName = _nickName.value,
                        ),
                        travelRoomInfo = TravelRoomDto(
                            budget = _travelBudget.value.toInt(),
                            endDate = _endDate.value,
                            startDate = _startDate.value,
                            travelName = _travelName.value
                        ),
                        imageFiles = imageFiles,
                    )
                )
            )
        }
    }

    /**
     * 초대받은 사용자 -> 여행방 참가
     */
    private val _joinTravelRoomResult =
        MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val joinTravelRoomResult = _joinTravelRoomResult.asStateFlow()
    fun joinTravelRoom(roomId: Int, imageFiles: MultipartBody.Part?) {
        Log.d(TAG, "joinTravelRoom: $roomId")
        viewModelScope.launch {
            _joinTravelRoomResult.emit(
                requestJoinUserUseCase.invoke(
                    roomId = roomId,
                    createTravelRoomDto = CreateTravelRoomDto(
                        travelUserInfo = TravelUserDto(
                            nickName = _nickName.value,
                        ),
                        travelRoomInfo = TravelRoomDto(),
                        imageFiles = imageFiles,
                    )
                )
            )
        }
    }
}