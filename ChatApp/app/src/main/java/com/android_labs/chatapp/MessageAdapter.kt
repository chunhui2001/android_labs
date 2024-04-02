package com.android_labs.chatapp

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

const val TYPE_MESSAGE_SENT = 0
const val TYPE_MESSAGE_RECEIVED = 1
const val TYPE_IMAGE_SENT = 2
const val TYPE_IMAGE_RECEIVED = 3

class MessageAdapter(private var currentUserName: String, private var messages: List<JSONObject>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SendMessageHolder(view: View): RecyclerView.ViewHolder(view) {
        private var userNameText = view.findViewById<TextView>(R.id.userNameText)
        private var messageText = view.findViewById<TextView>(R.id.messageText)

        fun bind(msg: JSONObject) {
            userNameText.text = msg.getString("name")
            messageText.text = msg.getString("message")
        }
    }

    inner class SendImageHolder(view: View): RecyclerView.ViewHolder(view) {
        private var userNameText = view.findViewById<TextView>(R.id.userNameText)
        private var messageImage = view.findViewById<ImageView>(R.id.messageImage)

        fun bind(msg: JSONObject) {
            userNameText.text = msg.getString("name")

            var bytes = android.util.Base64.decode(msg.getString("image"), android.util.Base64.DEFAULT)
            var img = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            messageImage.setImageBitmap(img)
        }
    }

    inner class ReceiveMessageHolder(view: View): RecyclerView.ViewHolder(view) {
        private var userNameText = view.findViewById<TextView>(R.id.userNameText)
        private var messageText = view.findViewById<TextView>(R.id.messageText)

        fun bind(msg: JSONObject) {
            userNameText.text = msg.getString("name")
            messageText.text = msg.getString("message")
        }
    }

    inner class ReceiveImageHolder(view: View): RecyclerView.ViewHolder(view) {
        private var userNameText = view.findViewById<TextView>(R.id.userNameText)
        private var messageImage = view.findViewById<ImageView>(R.id.messageImage)

        fun bind(msg: JSONObject) {
            userNameText.text = msg.getString("name")
            var bytes = android.util.Base64.decode(msg.getString("image"), android.util.Base64.DEFAULT)
            var img = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            messageImage.setImageBitmap(img)
        }
    }

    override fun getItemViewType(position: Int): Int {
        var msg = messages[position]
        var userName = msg.getString("name")

        if (userName.equals(currentUserName)) {
            if (msg.has("message")) {
                return TYPE_MESSAGE_SENT
            } else {
                return TYPE_IMAGE_SENT
            }
        } else {
            if (msg.has("message")) {
                return TYPE_MESSAGE_RECEIVED
            } else {
                return TYPE_IMAGE_RECEIVED
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TYPE_MESSAGE_SENT -> {
                var layout = LayoutInflater.from(parent.context).inflate(R.layout.item_message_layout, parent, false)
                return SendMessageHolder(layout)
            }
            TYPE_IMAGE_SENT -> {
                var layout = LayoutInflater.from(parent.context).inflate(R.layout.item_image_layout, parent, false)
                return SendImageHolder(layout)
            }
            TYPE_MESSAGE_RECEIVED -> {
                var layout = LayoutInflater.from(parent.context).inflate(R.layout.item_receive_message, parent, false)
                return ReceiveMessageHolder(layout)
            }
            TYPE_IMAGE_RECEIVED -> {
                var layout = LayoutInflater.from(parent.context).inflate(R.layout.item_receive_image, parent, false)
                return ReceiveImageHolder(layout)
            } else -> {
                var layout = LayoutInflater.from(parent.context).inflate(R.layout.item_message_layout, parent, false)
                return SendMessageHolder(layout)
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var userName = messages[position].getString("name")

        if (userName.equals(currentUserName)) {
            if (messages[position].has("message")) {
                (holder as SendMessageHolder).bind(messages[position])
            } else {
                (holder as SendImageHolder).bind(messages[position])
            }
        } else {
            if (messages[position].has("message")) {
                (holder as ReceiveMessageHolder).bind(messages[position])
            } else {
                (holder as ReceiveImageHolder).bind(messages[position])
            }
        }
    }
}