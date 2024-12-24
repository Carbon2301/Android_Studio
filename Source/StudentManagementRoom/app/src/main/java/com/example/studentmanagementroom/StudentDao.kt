package com.example.studentmanagementroom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {

    @Insert
    fun insert(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(students: List<Student>) // Hàm chèn nhiều sinh viên

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("SELECT * FROM student")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM student WHERE id = :id")
    fun getStudentById(id: Int): Student?

    @Query("SELECT * FROM student WHERE mssv LIKE :keyword OR hoten LIKE :keyword")
    fun searchStudents(keyword: String): List<Student>

    @Query("DELETE FROM Student WHERE id = :id")
    fun deleteById(id: Int)

    @Delete
    fun deleteStudents(students: List<Student>) // Hàm xóa nhiều sinh viên
}


