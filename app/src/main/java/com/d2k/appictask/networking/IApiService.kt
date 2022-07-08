package com.d2k.tmb.networking

import com.d2k.appictask.ui.login.model.LoginRequest
import com.d2k.appictask.ui.login.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IApiService {

    /**
     * Authenticate call.
     *
     * @param loginRequest the login request Ex:- <String,String>
     * @return the call
     */
    /*IAuthenticate this API Call Interface Is use to Login*/
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

}