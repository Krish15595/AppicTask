package com.d2k.appictask.networking

import android.content.Context
import com.d2k.appictask.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiClient @Inject constructor() {
    /*
        companion object {
            //    const val BASE_URL = "http://13.66.200.79:8008/"
            //    const val BASE_URL = "http://219.90.67.69:8011/OrderManagement_API/"
            const val BASE_URL = "http://ewsdemo.eastus2.cloudapp.azure.com:8080/TMB_Mobile_API/"
            const val UAT_URL = "http://192.168.1.11/TMB_Mobile_API/"
        }
        var TOKEN: String? = null
        val aPIService: IApiService
            get() {
                */
/* var httpClient = OkHttpClient.Builder()

             httpClient.addInterceptor(object : Interceptor{
                 override fun intercept(chain: Interceptor.Chain): Response {
                     var original : Request = chain.request()
                     var request : Request = original.newBuilder()
                         .header("version", 1.toString())
                         .header("Authorization","Bearer "+ TOKEN)
                         .method(original.method,original.body)
                         .build()

                     return chain.proceed(request)
                 }

             })*/
/*
            val retrofit = Retrofit.Builder()
            retrofit
                .baseUrl(Companion.UAT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(customLogInterceptor())
                .build()
            return retrofit.build().create(IApiService::class.java)
        }

    fun customLogInterceptor(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(CustomHttpLogging())
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .connectionSpecs(
                    Arrays.asList(
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.CLEARTEXT
                    )
                )
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .connectionSpecs(
                    Arrays.asList(
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.CLEARTEXT
                    )
                )
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()
        }
    }*/

    companion object {
        private const val BASE_URL = "https://reqres.in/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    private fun getRetrofitClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
}
