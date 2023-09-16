package com.moneyminions.domain.repository.login

import com.moneyminions.domain.model.login.LoginResultDto

interface LoginRepository {
    fun signInKakao()
}