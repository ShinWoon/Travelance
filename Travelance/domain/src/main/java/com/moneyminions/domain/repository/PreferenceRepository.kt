package com.moneyminions.domain.repository

import com.moneyminions.domain.model.login.JwtTokenDto


interface PreferenceRepository {
    fun putJwtToken(jwtTokenDto: JwtTokenDto)
    fun getJwtToken(): JwtTokenDto
//    fun putRole(role: String?)
//    fun getRole(): String
    fun putFCMToken(fcmToken: String)
    fun getFCMToken(): String
    
    fun putTravelingRoomId(roomId: Int)
    fun getTravelingRoomId(): Int
    fun refreshPreference()
}