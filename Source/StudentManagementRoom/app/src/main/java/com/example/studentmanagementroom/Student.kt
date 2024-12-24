package com.example.studentmanagementroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "mssv") val mssv: String,
    @ColumnInfo(name = "hoten") val hoten: String,
    @ColumnInfo(name = "ngaysinh") val ngaysinh: String,
    @ColumnInfo(name = "email") val email: String
)




