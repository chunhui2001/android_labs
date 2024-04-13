package com.android_labs.chatapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.io.ByteArrayOutputStream

//const val SERVER_URL = "ws://echo.websocket.org"
const val SERVER_URL = "ws://192.168.1.5:9100"
private const val TAG = "com.android_labs.chatapp.ChatActivity"
private const val REQUEST_IMAGE_CODE = 1

class ChatActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, userName: String): Intent {
            return Intent(context, ChatActivity::class.java).putExtra("userName", userName)
        }
    }

    private lateinit var webSocket: WebSocket

    private lateinit var currentUserName: String
    private lateinit var pickImageBtn: ImageView
    private lateinit var messageBoxInput: EditText
    private lateinit var sendMessageBtn: TextView
    private lateinit var messageListView: RecyclerView
    private var messages = mutableListOf<JSONObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        this.currentUserName = intent?.getStringExtra("userName")!!

        this.initWebSocket()
    }

    private fun initWebSocket() {
        this.webSocket = OkHttpClient().newWebSocket(
            Request.Builder().url(SERVER_URL).build(),
            EchoWebSocketListener()
        )
    }

    private fun initView() {
        this.pickImageBtn = findViewById(R.id.pickImageBtn)
        this.messageBoxInput = findViewById(R.id.messageBoxInput)
        this.sendMessageBtn = findViewById(R.id.sendMessageBtn)
        this.messageListView = findViewById(R.id.messageListView)

        this.messageBoxInput.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                var inputMessage = s.toString().trim()

                if (inputMessage.isNotEmpty()) {

                }
            }
        })

        this.sendMessageBtn.setOnClickListener {
            var inputMessage = this.messageBoxInput.text.toString().trim()

            if (inputMessage.isNotEmpty()) {
                sendText(inputMessage)
                this.messageBoxInput.setText("")
            }
        }

        this.pickImageBtn.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")

            startActivityForResult(Intent.createChooser(intent, "Pick a image"), REQUEST_IMAGE_CODE)
        }

        this.messageListView.adapter = MessageAdapter(currentUserName, messages)
        this.messageListView.layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (REQUEST_IMAGE_CODE == requestCode && resultCode == RESULT_OK) {
            data?.let { it1 ->
                it1.data?.let { it2 ->
                    var inputStream = contentResolver.openInputStream(it2)
                    BitmapFactory.decodeStream(inputStream)?.let { image ->
                        sendImage(image)
                    }
                }
            }
        }
    }

    private fun sendImage(image: Bitmap) {
        var outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

        var base64String = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)

        var message = JSONObject()

        message.put("name", currentUserName)
        message.put("image", base64String)
        message.put("timestamp", System.currentTimeMillis())

        this.webSocket.send(message.toString())
    }

    private fun sendText(msg: String) {
        var message = JSONObject()

        message.put("name", currentUserName)
        message.put("message", msg)
        message.put("timestamp", System.currentTimeMillis())

        this.webSocket.send(message.toString())
    }

    inner class EchoWebSocketListener() : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            runOnUiThread{
                Toast.makeText(this@ChatActivity, "Socket connection successful", Toast.LENGTH_SHORT).show()
                initView()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)

            runOnUiThread {
                if (text.startsWith("{") && text.endsWith("}")) {
                    var message = JSONObject(text)
                    // display message
                    this@ChatActivity.messages.add(message)
                    this@ChatActivity.messageListView.adapter!!.notifyDataSetChanged()
                    this@ChatActivity.messageListView.smoothScrollToPosition(this@ChatActivity.messages.size)
                } else {
                    Toast.makeText(this@ChatActivity, text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}