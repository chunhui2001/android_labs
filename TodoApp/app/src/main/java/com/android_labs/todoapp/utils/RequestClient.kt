package com.android_labs.todoapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val CONTENT_TYPE_FORM = "application/x-www-form-urlencoded"
const val CONTENT_TYPE_JSON = "application/json; charset=utf-8"

class RequestClient(private var ctx: Context) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(ctx)
    private val defaultRetryPolicy = DefaultRetryPolicy(3000, 3, 1.0f)

    interface RequestCallback {
        fun onSuccess(response: String);
    }

    fun sendPostRequest(
        apiUrl: String,
        contentType: String,
        params: Map<String, String>,
        cb: RequestCallback
    ) {
        this.sendPostRequest(apiUrl, contentType, emptyMap(), params, cb)
    }

    fun sendPostRequest(
        apiUrl: String,
        contentType: String,
        header: Map<String, String>,
        params: Map<String, String>,
        cb: RequestCallback
    ) {
        sendRequest(1, apiUrl, contentType, header, params, cb)
    }

    fun sendPutRequest(
        apiUrl: String,
        contentType: String,
        header: Map<String, String>,
        params: Map<String, String>,
        cb: RequestCallback
    ) {
        sendRequest(2, apiUrl, contentType, header, params, cb)
    }

    private fun sendRequest(
        method: Int,
        apiUrl: String,
        contentType: String,
        header: Map<String, String>,
        params: Map<String, String>,
        cb: RequestCallback
    ) {
        val stringRequest: StringRequest = object : StringRequest(
            method,
            apiUrl,
            { response ->
                response?.let {
                    cb.onSuccess(it)
                }

                Log.d("sendPostRequest: ", "response=$response")
            },
            { error ->
                handleError(error)
            }) {

            override fun getBody(): ByteArray {
                if (contentType == null || contentType!!.contains("json", true)) {
                    return JSONObject(params).toString().toByteArray(Charsets.UTF_8)
                }

                if (contentType!!.contains("urlencoded", true)) {
                    return getFormDataString(params).toByteArray(Charsets.UTF_8)
                }

                return JSONObject(params).toString().toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()

                contentType?.let {
                    params["Content-Type"] = it
                }

                for (h in header) {
                    params[h.key] = h.value
                }

                return params
            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                return try {
                    val utf8String = String(response.data, StandardCharsets.UTF_8)
                    Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response))
                } catch (e: UnsupportedEncodingException) {
                    Response.error(ParseError(e))
                }
            }
        }

        stringRequest.retryPolicy = defaultRetryPolicy

        logToCurlRequest(stringRequest)
        requestQueue.add(stringRequest)
    }

    fun sendGetRequest(
        apiUrl: String,
        header: Map<String, String>,
        cb: RequestCallback
    ) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET,
            apiUrl,
            { response ->
                response?.let {
                    cb.onSuccess(it)
                }
            },
            { error ->
                handleError(error)
            }) {

            override fun getHeaders(): Map<String, String> {
                return header
            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                return try {
                    val utf8String = String(response.data, StandardCharsets.UTF_8)
                    Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response))
                } catch (e: UnsupportedEncodingException) {
                    Response.error(ParseError(e))
                }
            }
        }

        stringRequest.retryPolicy = defaultRetryPolicy

        logToCurlRequest(stringRequest)
        requestQueue.add(stringRequest)
    }

    fun sendDeleteRequest(
        apiUrl: String,
        header: Map<String, String>,
        cb: RequestCallback
    ) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.DELETE,
            apiUrl,
            { response ->
                response?.let {
                    cb.onSuccess(it)
                }
            },
            { error ->
                handleError(error)
            }) {

            override fun getHeaders(): Map<String, String> {
                return header
            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                return try {
                    val utf8String = String(response.data, StandardCharsets.UTF_8)
                    Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response))
                } catch (e: UnsupportedEncodingException) {
                    Response.error(ParseError(e))
                }
            }
        }

        stringRequest.retryPolicy = defaultRetryPolicy

        logToCurlRequest(stringRequest)
        requestQueue.add(stringRequest)
    }

    private fun handleError(error: VolleyError) {
        val errorResponse = error.networkResponse
        var errorMessage: String

        if (error is ServerError && errorResponse != null) {
            errorMessage = String(errorResponse.data, Charsets.UTF_8)
        } else {
            errorMessage = if (error.message == null) {
                "N/a"
            } else {
                error.message.toString()
            };
        }

        Toast.makeText(ctx, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun logToCurlRequest(request: Request<*>) {
        val builder = StringBuilder()

        builder.append("curl ")
        builder.append("-vX \"")

        when (request.method) {
            Request.Method.POST -> builder.append("POST")
            Request.Method.GET -> builder.append("GET")
            Request.Method.PUT -> builder.append("PUT")
            Request.Method.DELETE -> builder.append("DELETE")
        }

        builder.append("\"")

        try {
            if (request.body != null) {
                builder.append(" -d ")
                builder.append("'")
                builder.append(String(request.body))
                builder.append("'")
            }

            for (key in request.headers.keys) {
                builder.append(" -H '")
                builder.append(key)
                builder.append(": ")
                builder.append(request.headers[key])
                builder.append("'")
            }

            builder.append(" \"")
            builder.append(request.url)
            builder.append("\"")

            Log.d("VolleyLog", builder.toString())
        } catch (e: AuthFailureError) {
            Log.e(
                "Unable to get body of response or headers for curl logging",
                e.message.toString()
            )
        }
    }

    fun getFormDataString(formData: Map<String, String>): String {
        val postData = StringBuilder()

        for ((key, value) in formData) {
            if (postData.isNotEmpty()) {
                postData.append('&')
            }
            postData.append(URLEncoder.encode(key, StandardCharsets.UTF_8.toString()))
            postData.append('=')
            postData.append(URLEncoder.encode(value, StandardCharsets.UTF_8.toString()))
        }

        return postData.toString()
    }
}