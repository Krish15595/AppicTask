package com.d2k.appictask.ui.main.model

data class Hierarchy(
    val accountNumber: String?,
    val brandNameList: List<BrandName?>?,
    val isSelected: Boolean? = false
)