package com.example.itis_android_tasks.data.model

data class UserModel (
    val name : String,
    val phone : String,
    val email : String,
    val password : String,
    val deletedDate: Long?
)