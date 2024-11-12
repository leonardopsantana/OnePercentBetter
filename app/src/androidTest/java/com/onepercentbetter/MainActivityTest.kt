package com.onepercentbetter

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.onepercentbetter.core.datastore.token.TokenRepository
import com.onepercentbetter.core.model.AuthToken
import com.onepercentbetter.core.model.RefreshToken
import com.onepercentbetter.core.model.Token
import com.onepercentbetter.login.ui.LoginScreen
import com.onepercentbetter.tasklist.ui.TaskListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var tokenRepository: com.onepercentbetter.core.datastore.token.TokenRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun showLoginWhenNotLoggedIn() {
        runBlocking {
            // Clearing token simulates being logged out
            tokenRepository.clearToken()
        }

        composeTestRule
            .onNodeWithTag(LoginScreen.TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun showTaskListWhenLoggedIn() {
        runBlocking {
            // Storing a token simulates being logged in
            tokenRepository.storeToken(
                token = Token(
                    authToken = AuthToken("Test"),
                    refreshToken = RefreshToken("Test"),
                ),
            )
        }

        composeTestRule
            .onNodeWithTag(TaskListScreen.TEST_TAG)
            .assertIsDisplayed()
    }
}
