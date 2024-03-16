package com.android_labs.websocketokhttp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

private const val INTERNET_PERMISSION_CODE = 1
private const val TAG = "com.android_labs.websocketokhttp.MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var startBtn: Button
    private lateinit var outputTxt: TextView

    private lateinit var okhttp: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.startBtn = findViewById(R.id.startBtn)
        this.outputTxt = findViewById(R.id.outputTxt)

        this.okhttp = OkHttpClient()

        this.startBtn.isEnabled = hasPermission(Manifest.permission.INTERNET)

        this.startBtn.setOnClickListener {

            startWebSocket()
        }

        if (!this.startBtn.isEnabled) {
            requestPermission(Manifest.permission.INTERNET, INTERNET_PERMISSION_CODE)
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this@MainActivity, permission) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == INTERNET_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.startBtn.isEnabled = true
            }
        }
    }

    private fun startWebSocket() {
        val url = "ws://echo.websocket.org"
        val request = Request.Builder().url(url).build()

        this.okhttp.newWebSocket(request, EchoWebSocketListener())
        this.okhttp.dispatcher().executorService().shutdown()
    }

    inner class EchoWebSocketListener() : WebSocketListener() {

        private val NORMAL_CLOSURE_STATUS = 1000

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            output("Error: " + t.message)
            Log.e(TAG, "Error: " + t.message)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            webSocket.send("Hello, it's Laurel!")
            webSocket.send("What's up?")
            // webSocket.send(ByteString.decodeHex("Try it!"))
            webSocket.close(NORMAL_CLOSURE_STATUS, "Good bye!")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            output("Receiving Bytes: " + bytes.hex())
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            output("Receiving Text: $text")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            output("Closing: $code/$reason")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            // webSocket.close(NORMAL_CLOSURE_STATUS, null)
            output("Closed: $code/$reason")
        }

        private fun output(s: String) {
            runOnUiThread {
                val color = ContextCompat.getColor(this@MainActivity, R.color.primary)
                val hexColor: String = String.format("#%06X", color)
                val text = SpannableString(outputTxt.text.toString() + "\r\n" + s ?: "")
                val span = ForegroundColorSpan(Color.parseColor(hexColor));

                text.setSpan(span,0,text.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

                outputTxt.text = text
            }
        }
    }
}