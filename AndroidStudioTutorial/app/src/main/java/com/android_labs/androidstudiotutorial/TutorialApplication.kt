package com.android_labs.androidstudiotutorial

import android.app.Application
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class TutorialApplication: Application() {

    override fun onCreate() {
        super.onCreate()

//        HttpsTrustManager.allowAllSSL()
//        trustAllCertificates()
    }
    companion object {
        fun trustAllCertificates() {
            try {
                // 创建一个信任所有证书的 TrustManager
                val trustAllManager = object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                        // 信任所有客户端证书
                    }

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                        // 信任所有服务器证书
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return emptyArray() // 返回空数组表示信任所有颁发机构
                    }
                }

                // 获取 SSLContext
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf<TrustManager>(trustAllManager), null)

                // 将信任所有证书的 SSLContext 设置给 HttpsURLConnection
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)

                // 禁用主机名验证
                HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}