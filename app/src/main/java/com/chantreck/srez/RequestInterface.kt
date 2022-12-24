package com.chantreck.srez

import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RequestInterface {
    @POST("login")
    fun login(@Body body: LoginBody): Call<LoginResponse>

    @GET("user")
    fun getUserInfo(): Call<UserInfoResponse>

    @GET("user/tasks")
    fun getTasks(): Call<TaskResponse>
}

@Serializable
data class LoginBody(
    val email: String,
    val password: String,
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val data: LoginResponseData,
    val message: String,
)

@Serializable
data class LoginResponseData(
    val token: String,
    val name: String,
)

@Serializable
data class UserInfoResponse(
    val success: Boolean,
    val data: UserInfo,
    val message: String,
)

@Serializable
data class UserInfo(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val skill_id: Int,
    val skill: Skill,
    val avatar: String,
)

@Serializable
data class Skill(
    val id: Int,
    val title: String,
)

@Serializable
data class TaskResponse(
    val success: Boolean,
    val data: List<Task>,
    val message: String,
)

@Serializable
data class Task(
    val id: Int,
    val title: String,
    val user_id: Int,
    val description_url: String,
    val actual_duration: Int,
    val deadline: String,
    val estimated_duration: Int,
    val status_id: Int,
    val status: TaskStatus
)

@Serializable
data class TaskStatus(
    val id: Int,
    val title: String
)