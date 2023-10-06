package com.moneyminions.presentation.utils

import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.presentation.R

object Constants {

    val ACCOUNT_LOGO_LIST = listOf(
        R.drawable.image_scjaeil, R.drawable.image_kbkm, R.drawable.image_ibkku, R.drawable.image_nhnh, R.drawable.image_daegu,
        R.drawable.image_kdbsu, R.drawable.image_mgsmu, R.drawable.image_shinhan, R.drawable.image_we, R.drawable.image_hana
    )
    val AUTHENTICATION_ACCOUNT_LIST = listOf(
        AccountDto(idx = 0, bankName = "SC제일"), AccountDto(idx = 1, bankName = "KB국민"), AccountDto(idx = 2, bankName = "IBK기업"),
        AccountDto(idx = 3, bankName = "NH농협"), AccountDto(idx = 4, bankName = "대구"), AccountDto(idx = 5, bankName = "KDB산업"),
        AccountDto(idx = 6, bankName = "새마을"), AccountDto(idx = 7, bankName = "신한"), AccountDto(idx = 8, bankName = "우리"),
        AccountDto(idx = 9, bankName = "하나")
    )
    val CARD_FRAME_LIST = listOf(
        R.drawable.ic_credit_card_bc, R.drawable.ic_credit_card_kb, R.drawable.ic_credit_card_nh, R.drawable.ic_credit_card_lotte,
        R.drawable.ic_credit_card_samsung, R.drawable.ic_credit_card_shinhan, R.drawable.ic_credit_card_hana, R.drawable.ic_credit_card_hyundai,
    )
    val ACCOUNT_FRAME_LIST = listOf(
        R.drawable.ic_account_jaeil, R.drawable.ic_account_kb, R.drawable.ic_account_ibk, R.drawable.ic_account_nh, R.drawable.ic_account_daegu,
        R.drawable.ic_account_kdb, R.drawable.ic_account_saemaeul, R.drawable.ic_account_shinhan, R.drawable.ic_account_we, R.drawable.ic_account_hana
    )

    // Notification
    const val CHANNEL_ID = "travelance_channel"
    const val CHANNEL_NAME = "TRAVELANCE"


}