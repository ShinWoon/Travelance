package com.moneyminions.domain.repository

import com.moneyminions.domain.model.login.JwtTokenDto

interface PreferenceRepository {
    fun putJwtToken(jwtTokenDto: JwtTokenDto)
    fun putRole(role: String)
}