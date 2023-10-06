package com.moneyminions.presentation.viewmodel.announcement

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.home.AnnouncementDto
import com.moneyminions.domain.model.home.AnnouncementEditDto
import com.moneyminions.domain.usecase.home.DeleteAnnouncementUseCase
import com.moneyminions.domain.usecase.home.EditAnnouncementUseCase
import com.moneyminions.domain.usecase.home.GetAnnouncementListUseCase
import com.moneyminions.domain.usecase.home.SaveAnnouncementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AnnouncementViewModel_D210"
@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val saveAnnouncementUseCase: SaveAnnouncementUseCase,
    private val getAnnouncementListUseCase: GetAnnouncementListUseCase,
    private val deleteAnnouncementUseCase: DeleteAnnouncementUseCase,
    private val editAnnouncementUseCase: EditAnnouncementUseCase,
) : ViewModel() {
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
        _link.value = link
    }
    
    private val _selectNoticeId = mutableStateOf(0)
    val selectNoticeId: State<Int> = _selectNoticeId
    fun setSelectNoticeId(id: Int) {
        _selectNoticeId.value = id
    }
    
    private val _announcementList = mutableStateOf(mutableListOf<AnnouncementDto>())
    val announcementList: State<MutableList<AnnouncementDto>> = _announcementList
    fun refleshAnnouncementList(list: MutableList<AnnouncementDto>) {
        _announcementList.value = list
    }
    
    fun inputReset() {
        _title.value = ""
        _content.value = ""
        _link.value = ""
        _selectNoticeId.value = 0
    }
    fun checkInput(): Boolean {
        return _title.value.isNotEmpty() && _content.value.isNotEmpty()
    }
    
    /**
     * 공지사항 저장
     */
    private val _saveAnnouncementResult =
        MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val saveAnnouncementResult = _saveAnnouncementResult.asStateFlow()
    fun saveAnnouncement(roomId: Int) {
        _saveAnnouncementResult.value = NetworkResult.Loading
        viewModelScope.launch {
            _saveAnnouncementResult.emit(
                saveAnnouncementUseCase.invoke(
                    AnnouncementDto(
                        id = roomId,
                        title = _title.value,
                        content = _content.value,
                        link = _link.value,
                    )
                )
            )
        }
    }
    
    /**
     * 공지사항 리스트 GET
     */
    private val _getAnnouncementListResult =
        MutableStateFlow<NetworkResult<List<AnnouncementDto>>>(NetworkResult.Idle)
    val getAnnouncementListResult = _getAnnouncementListResult.asStateFlow()
    fun getAnnouncementList(roomId: Int) {
        _getAnnouncementListResult.value = NetworkResult.Loading
        viewModelScope.launch {
            _getAnnouncementListResult.emit(getAnnouncementListUseCase.invoke(roomId = roomId))
        }
    }
    
    /**
     * 공지사항 삭제
     */
    private val _deleteAnnouncementResult =
        MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val deleteAnnouncementResult = _deleteAnnouncementResult.asStateFlow()
    fun deleteAnnouncement(roomId: Int, noticeId: Int) {
        Log.d(TAG, "deleteAnnouncement: 삭제 요청 $roomId, $noticeId")
        _deleteAnnouncementResult.value = NetworkResult.Loading
        viewModelScope.launch {
            _deleteAnnouncementResult.emit(
                deleteAnnouncementUseCase.invoke(
                    roomId = roomId,
                    noticeId = noticeId
                )
            )
        }
    }

    
    /**
     * 공지사항 수정
     */
    private val _editAnnouncementResult =
        MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val editAnnouncementResult = _editAnnouncementResult.asStateFlow()
    fun editAnnouncement(roomId: Int) {
        _editAnnouncementResult.value = NetworkResult.Loading
        viewModelScope.launch {
            _editAnnouncementResult.emit(
                editAnnouncementUseCase.invoke(
                    roomId = roomId,
                    AnnouncementEditDto(
                        id = _selectNoticeId.value,
                        title = _title.value,
                        content = _content.value,
                        link = _link.value,
                    )
                )
            )
        }
    }
    
}