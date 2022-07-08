package com.d2k.appictask.ui.main.model

data class BrandName(
    val brandName: String?,
    val locationNameList: List<LocationName?>?,
    val isSelected: Boolean? = false
)