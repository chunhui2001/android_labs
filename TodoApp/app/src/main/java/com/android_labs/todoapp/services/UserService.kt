package com.android_labs.todoapp.services

import android.content.Context
import com.android_labs.todoapp.models.UserInfo
import com.android_labs.todoapp.utils.CONTENT_TYPE_FORM
import com.android_labs.todoapp.utils.CONTENT_TYPE_JSON
import com.android_labs.todoapp.utils.RequestClient
import com.android_labs.todoapp.utils.SharedPreference
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val API_URL_TOKEN = "https://192.168.1.5:9010/access_token"
private const val API_URL_REGISTER = "https://192.168.1.5:9010/register"
private const val API_URL_USER_INFO = "https://192.168.1.5:9010/user-info"

const val API_URL_USER_DEFAULT_IMG = "https://192.168.1.5:9010/m/pp/not_found"
const val API_URL_USER_HEAD_IMG = "https://192.168.1.5:9010/m/pp"

private const val USER_TOKEN_KEY = "access_token_v3";
private const val USER_INFO_KEY = "user_info_v3";

class UserService(ctx: Context) {

    private var requestClient = RequestClient(ctx)
    private var sharedPreference = SharedPreference(ctx)

    fun isLogin(): Boolean {
        return this.sharedPreference.getStringValue(USER_TOKEN_KEY).isNotEmpty()
    }

    fun logout() {
        this.sharedPreference.removeKey(USER_TOKEN_KEY)
        this.sharedPreference.removeKey(USER_INFO_KEY)
    }

    fun userToken(): String {
        return this.sharedPreference.getStringValue(USER_TOKEN_KEY)
    }

    fun userInfo(onCompleted: (UserInfo?) -> Unit) {
        val userInfoResultString = this.sharedPreference.getStringValue(USER_INFO_KEY);

        if (userInfoResultString.isNotEmpty()) {
            val type = object : TypeToken<R<UserInfo>>() {}.type
            val userInfoResult: R<UserInfo> = Gson().fromJson(userInfoResultString, type)

            if (userInfoResult.isSuccess) {
                onCompleted(userInfoResult.data)
                return
            }
        }

        val userToken = this.userToken()

        if (userToken.isEmpty()) {
            onCompleted(null)
            return
        }

        // 根据 token 查询用户信息
        requestClient.sendGetRequest(
            API_URL_USER_INFO,
            mapOf(
                Pair("Authorization", "Bearer $userToken"),
                Pair("Accept", "application/json"),
            ),
            object : RequestClient.RequestCallback {
                override fun onSuccess(userInfoResultString: String) {
                    sharedPreference.setStringValue(USER_INFO_KEY, userInfoResultString)

                    val type = object : TypeToken<R<UserInfo>>() {}.type
                    val userInfoResult: R<UserInfo> = Gson().fromJson(userInfoResultString, type)

                    if (userInfoResult.isSuccess) {
                        onCompleted(userInfoResult.data)
                        return
                    }
                }
            })
    }

    fun saveAccessToken(token: String) {
        this.sharedPreference.setStringValue(USER_TOKEN_KEY, token)
    }

    fun <T> accessToken(username: String, passwd: String, onCompleted: (R<T>) -> Unit) {
        requestClient.sendPostRequest(
            API_URL_TOKEN,
            CONTENT_TYPE_JSON,
            mapOf(
                Pair("username", username),
                Pair("password", passwd),
                Pair("csrf_token", "csrf_token"),
            ),
            object : RequestClient.RequestCallback {
                override fun onSuccess(response: String) {
                    val type = object : TypeToken<R<T>>() {}.type
                    val result: R<T> = Gson().fromJson(response, type)
                    onCompleted(result)
                }
            })
    }

    fun signup(username: String, passwd: String, email: String, onCompleted: (R<Boolean>) -> Unit) {
        requestClient.sendPostRequest(
            API_URL_REGISTER,
            CONTENT_TYPE_FORM,
            mapOf(
                Pair("username", username),
                Pair("password", passwd),
                Pair("useremail", email),
            ),
            object : RequestClient.RequestCallback {
                override fun onSuccess(response: String) {
                    val type = object : TypeToken<R<Boolean>>() {}.type
                    val result: R<Boolean> = Gson().fromJson(response, type)
                    onCompleted(result)
                }
            })
    }
}