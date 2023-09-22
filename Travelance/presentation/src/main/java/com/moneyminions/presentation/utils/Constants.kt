package com.moneyminions.presentation.utils

import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.presentation.R

object Constants {

    val ACCOUNT_LOGO_LIST = listOf(
        R.drawable.image_scjaeil, R.drawable.image_kbkm, R.drawable.image_ibkku, R.drawable.image_nhnh, R.drawable.image_daegu,
        R.drawable.image_kdbsu, R.drawable.image_mgsmu, R.drawable.image_shinhan, R.drawable.image_we, R.drawable.image_hana
    )
    val AUTHENTICATION_ACCOUNT_LIST = listOf(
        AccountDto(idx = 0, name = "SC제일"), AccountDto(idx = 1, name = "KB국민"), AccountDto(idx = 2, name = "IBK기업"),
        AccountDto(idx = 3, name = "NH농협"), AccountDto(idx = 4, name = "대구"), AccountDto(idx = 5, name = "KDB산업"),
        AccountDto(idx = 6, name = "새마을"), AccountDto(idx = 7, name = "신한"), AccountDto(idx = 8, name = "우리"),
        AccountDto(idx = 9, name = "하나")
    )

}