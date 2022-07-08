package com.d2k.appictask.ui.main.model

data class LocationName(
    val locationName: String?,
    val merchantNumber: List<MerchantNumber?>?,
    val isSelected: Boolean? = false
)