package com.onepercentbetter.fakes

import com.onepercentbetter.core.datastore.Preferences

class FakePreferences : com.onepercentbetter.core.datastore.Preferences {
    private val storedInts: MutableMap<String, Int?> = mutableMapOf()
    private val storedBooleans: MutableMap<String, Boolean?> = mutableMapOf()

    override suspend fun storeInt(
        key: String,
        value: Int?,
    ) {
        storedInts[key] = value
    }

    override suspend fun getInt(
        key: String,
        defaultValue: Int?,
    ): Int? {
        return storedInts[key] ?: defaultValue
    }

    override suspend fun storeBoolean(
        key: String,
        value: Boolean,
    ) {
        storedBooleans[key] = value
    }

    override suspend fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Boolean {
        return storedBooleans[key] ?: defaultValue
    }
}
