package com.moneyminions.presentation.viewmodel.game

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.friend.FriendDto
import com.moneyminions.domain.model.home.TravelRoomFriendDto
import com.moneyminions.domain.usecase.home.GetTravelRoomFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "ChooseTeamGameViewModel"

@HiltViewModel
class ChooseTeamGameViewModel @Inject constructor(
    private val travelRoomFriendsUseCase: GetTravelRoomFriendsUseCase
) : ViewModel() {

    private val _getTravelRoomFriendsState = MutableStateFlow<NetworkResult<List<TravelRoomFriendDto>>>(
        NetworkResult.Idle)
    val getTravelRoomFriendState = _getTravelRoomFriendsState.asStateFlow()


        data class FriendDto(val email: String, val nickName: String, val profileUrl: String)

    val teammate = mutableListOf(
        FriendDto("scoups@example.com", "에스쿱스", "https://i.namu.wiki/i/-qrEP8xH9rwiWXnV5aTPgpuFORmvdzm-g-sK3v_Rg456mVv7gDnCOW8xpNxQ2GTl2SHlde8xmVmijBFZRMEeNlAMe0qyKvRH15reR-7D1aTMi5Cl88qOSXKBwHAQKBDpZ3hltnmjXnOhMDDUKR-5yw.webp"),
        FriendDto("jeonghan@example.com", "정한", "image_url_2"),
        FriendDto("joshua@example.com", "조슈아", "image_url_3"),
        FriendDto("jun@example.com", "준", "image_url_4"),
        FriendDto("hoshi@example.com", "호시", "image_url_5"),
        FriendDto("wonwoo@example.com", "원우", "image_url_6"),
        FriendDto("woozi@example.com", "우지", "image_url_7"),
        FriendDto("dokyeom@example.com", "도겸", "image_url_8"),
        FriendDto("mingyu@example.com", "민규", "image_url_9"),
        FriendDto("the8@example.com", "디에잇", "image_url_10"),
        FriendDto("seungkwan@example.com", "승관", "image_url_11"),
        FriendDto("vernon@example.com", "버논", "image_url_12"),
        FriendDto("dino@example.com", "디노", "image_url_13"),
    )

    private var _teamCnt = MutableStateFlow(0)
    var teamCnt = _teamCnt.asStateFlow()
//    val teamCnt = 3
    private val teams = Array(teamCnt.value) { mutableListOf<Int>() }
    // api로 해당 방의 친구 목록 불러 오기

    private var _participantsList = MutableStateFlow(listOf<FriendDto>())
    var participantsList = _participantsList.asStateFlow()

    fun chooseTeam() {
//        val teammateList = Array(teammate.size) { it }
        val teammateList = (0 until 12).toList()
        val shuffledList = teammateList.shuffled()

        for ((index, teammate) in shuffledList.withIndex()) {
            val teamIndex = index % teamCnt.value
            teams[teamIndex].add(teammate)
        }

        for ((index, team) in teams.withIndex()) {
            println("팀 ${index + 1}: ${team.joinToString(", ")}")
        }
    }

    // email, nickname, profileUrl
    fun getTeammateInfo(): Array<MutableList<FriendDto>> {
        Log.d(TAG, "getTeammateInfo: ${teamCnt.value}")
        chooseTeam()
        val teammateRandomList = Array(teamCnt.value) { mutableListOf<FriendDto>() }
        for ((index, team) in teams.withIndex()) {
            for (teammateInfo in team) {
                teammateRandomList[index].add(teammate[teammateInfo])
                Log.d(TAG, "getTeammateInfo: ${teammate[teammateInfo]}")
            }
            Log.d(TAG, "getTeammateInfo: ${teammateRandomList[index]}")
        }
        return teammateRandomList
    }
}
