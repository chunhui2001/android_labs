package com.android_labs.todoapp.services

import android.content.Context
import android.widget.Toast
import com.android_labs.todoapp.models.TodoModel
import com.android_labs.todoapp.utils.CONTENT_TYPE_JSON
import com.android_labs.todoapp.utils.RequestClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val API_TODO_LIST = "https://192.168.1.5:9010/todo/list_task?p_index=0&p_size=100"
private const val API_TODO_DELETE = "https://192.168.1.5:9010/todo/del_task"
private const val API_TODO_SAVE = "https://192.168.1.5:9010/todo/new_task"
private const val API_TODO_UPDATE = "https://192.168.1.5:9010/todo/task"

class TodoService(var ctx: Context) {

    private var requestClient = RequestClient(ctx)

    fun list(userToken: String, onCompleted: (MutableList<TodoModel>) -> Unit) {
        // 查询列表
        requestClient.sendGetRequest(
            API_TODO_LIST,
            mapOf(
                Pair("Authorization", "Bearer $userToken"),
                Pair("Accept", "application/json"),
            ),
            object : RequestClient.RequestCallback {
                override fun onSuccess(response: String) {
                    val type = object : TypeToken<R<List<TodoModel>>>() {}.type
                    val result: R<List<TodoModel>> = Gson().fromJson(response, type)

                    if (result.isSuccess) {
                        onCompleted(result.data.toMutableList())
                    } else {
                        Toast.makeText(ctx, result.message, Toast.LENGTH_SHORT).show();
                    }
                }
            })
    }

    fun delete(userToken: String, id: String, onCompleted: (Boolean) -> Unit) {
        requestClient.sendDeleteRequest(
            "$API_TODO_DELETE?id=$id",
            mapOf(
                Pair("Authorization", "Bearer $userToken"),
                Pair("Accept", "application/json"),
            ),
            object : RequestClient.RequestCallback {
                override fun onSuccess(response: String) {
                    val type = object : TypeToken<R<Boolean>>() {}.type
                    val result: R<Boolean> = Gson().fromJson(response, type)

                    if (result.isSuccess) {
                        onCompleted(result.data)
                    } else {
                        Toast.makeText(ctx, result.message, Toast.LENGTH_SHORT).show();
                    }
                }
            })
    }

    fun save(userToken: String, title: String, desc: String, onCompleted: (String) -> Unit) {
        requestClient.sendPostRequest(
            "$API_TODO_SAVE",
            CONTENT_TYPE_JSON,
            mapOf(
                Pair("Authorization", "Bearer $userToken"),
                Pair("Accept", "application/json"),
            ),
            mapOf(
                Pair("title", title),
                Pair("description", desc),
            ),
            object : RequestClient.RequestCallback {
                override fun onSuccess(response: String) {
                    val type = object : TypeToken<R<String>>() {}.type
                    val result: R<String> = Gson().fromJson(response, type)

                    if (result.isSuccess) {
                        onCompleted(result.data)
                    } else {
                        Toast.makeText(ctx, result.message, Toast.LENGTH_SHORT).show();
                    }
                }
            })
    }

    fun update(userToken: String, model: TodoModel, onCompleted: (Boolean) -> Unit) {
        requestClient.sendPutRequest(
            "$API_TODO_UPDATE/${model.id}",
            CONTENT_TYPE_JSON,
            mapOf(
                Pair("Authorization", "Bearer $userToken"),
                Pair("Accept", "application/json"),
            ),
            mapOf(
                Pair("title", model.title),
                Pair("description", model.description),
            ),
            object : RequestClient.RequestCallback {
                override fun onSuccess(response: String) {
                    val type = object : TypeToken<R<Boolean>>() {}.type
                    val result: R<Boolean> = Gson().fromJson(response, type)

                    if (result.isSuccess) {
                        onCompleted(result.data)
                    } else {
                        Toast.makeText(ctx, result.message, Toast.LENGTH_SHORT).show();
                    }
                }
            })
    }
}