package com.d2k.tmb.base

data class CommanModel<T> (var state:Int, var msg:String, var data:T, val results: Results,var statusCode : Int?){
    data class Results(
        val msg: String,
        val status: Int
    )
}
