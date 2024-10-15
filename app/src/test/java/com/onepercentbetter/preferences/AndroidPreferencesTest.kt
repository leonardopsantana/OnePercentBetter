package com.onepercentbetter.preferences

import android.content.SharedPreferences
import com.onepercentbetter.InstantTaskCoroutinesExecutorRule
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AndroidPreferencesTest {
    @get:Rule
    val instantTaskCoroutinesExecutorRule = InstantTaskCoroutinesExecutorRule()

    private val mockSharedPreferences: SharedPreferences = mockk()
    private val mockEditor: SharedPreferences.Editor = mockk()

    private val androidPreferences = com.onepercentbetter.core.datastore.AndroidPreferences(
        sharedPreferences = mockSharedPreferences,
    )

    private val testKey = "Test Key"

    @Before
    fun setUp() {
        every {
            mockSharedPreferences.edit()
        } returns mockEditor

        every {
            mockEditor.apply()
        } just runs
    }

    @Test
    fun storeNonNullInt() =
        runTest {
            every {
                mockEditor.putInt(testKey, 5)
            } returns mockEditor

            androidPreferences.storeInt(testKey, 5)

            verify {
                mockEditor.putInt(testKey, 5)
                mockEditor.apply()
            }
        }

    @Test
    fun storeNullInt() =
        runTest {
            every {
                mockEditor.remove(testKey)
            } returns mockEditor

            androidPreferences.storeInt(testKey, null)

            verify {
                mockEditor.remove(testKey)
                mockEditor.apply()
            }
        }
}
