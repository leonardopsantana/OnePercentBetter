package com.onepercentbetter.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PersistableTask::class],
    version = 1,
)
abstract class OPBDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO
}
