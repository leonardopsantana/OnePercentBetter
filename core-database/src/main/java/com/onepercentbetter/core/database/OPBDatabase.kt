package com.onepercentbetter.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onepercentbetter.core.database.entity.PersistableTask

@Database(
    entities = [PersistableTask::class],
    version = 1,
)
abstract class OPBDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO
}
