package com.example.studentmanagementroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 2)  // Tăng số phiên bản lên 2
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_database"
                )
                    .fallbackToDestructiveMigration()  // Tự động xóa cơ sở dữ liệu khi schema thay đổi
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

