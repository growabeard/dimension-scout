package com.witt.dimensionscout.presentation.characters.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.witt.dimensionscout.R
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class CharacterSearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun ensure_placeholder_is_displayed_when_query_is_empty() {
        composeTestRule.setContent {
            DimensionScoutTheme {
                CharacterSearchBar(
                    query = "",
                    onQueryChange = {},
                    loading = false,
                    showClearButton = false,
                    onClearInputClick = {},
                    onSearch = {}
                )
            }
        }

        val placeholderText = context.getString(R.string.search_placeholder)
        composeTestRule.onNodeWithText(placeholderText).assertIsDisplayed()
    }

    @Test
    fun ensure_query_is_displayed_correctly() {
        val testQuery = "Rick Sanchez"
        composeTestRule.setContent {
            DimensionScoutTheme {
                CharacterSearchBar(
                    query = testQuery,
                    onQueryChange = {},
                    loading = false,
                    showClearButton = true,
                    onClearInputClick = {},
                    onSearch = {}
                )
            }
        }

        composeTestRule.onNodeWithText(testQuery).assertIsDisplayed()
    }

    @Test
    fun ensure_clear_button_is_shown_when_requested() {
        composeTestRule.setContent {
            DimensionScoutTheme {
                CharacterSearchBar(
                    query = "Rick",
                    onQueryChange = {},
                    loading = false,
                    showClearButton = true,
                    onClearInputClick = {},
                    onSearch = {}
                )
            }
        }

        val clearDescription = context.getString(R.string.clear_search_input)
        composeTestRule.onNodeWithContentDescription(clearDescription).assertIsDisplayed()
    }

    @Test
    fun ensure_clear_button_is_hidden_when_not_requested() {
        composeTestRule.setContent {
            DimensionScoutTheme {
                CharacterSearchBar(
                    query = "Rick",
                    onQueryChange = {},
                    loading = false,
                    showClearButton = false,
                    onClearInputClick = {},
                    onSearch = {}
                )
            }
        }

        val clearDescription = context.getString(R.string.clear_search_input)
        composeTestRule.onNodeWithContentDescription(clearDescription).assertDoesNotExist()
    }

    @Test
    fun ensure_clicking_clear_button_triggers_callback() {
        val onClearMock = mockk<() -> Unit>(relaxed = true)
        composeTestRule.setContent {
            DimensionScoutTheme {
                CharacterSearchBar(
                    query = "Rick",
                    onQueryChange = {},
                    loading = false,
                    showClearButton = true,
                    onClearInputClick = onClearMock,
                    onSearch = {}
                )
            }
        }

        val clearDescription = context.getString(R.string.clear_search_input)
        composeTestRule.onNodeWithContentDescription(clearDescription).performClick()

        verify { onClearMock.invoke() }
        confirmVerified(onClearMock)
    }

    @Test
    fun ensure_typing_triggers_onQueryChange() {
        val onQueryChangeMock = mockk<(String) -> Unit>(relaxed = true)
        composeTestRule.setContent {
            DimensionScoutTheme {
                CharacterSearchBar(
                    query = "",
                    onQueryChange = onQueryChangeMock,
                    loading = false,
                    showClearButton = false,
                    onClearInputClick = {},
                    onSearch = {}
                )
            }
        }

        val placeholderText = context.getString(R.string.search_placeholder)
        composeTestRule.onNodeWithText(placeholderText).performTextInput("Morty")

        verify { onQueryChangeMock.invoke("Morty") }
        confirmVerified(onQueryChangeMock)
    }

    @Test
    fun ensure_pressing_search_IME_action_triggers_onSearch() {
        val onSearchMock = mockk<() -> Unit>(relaxed = true)
        composeTestRule.setContent {
            DimensionScoutTheme {
                CharacterSearchBar(
                    query = "Rick",
                    onQueryChange = {},
                    loading = false,
                    showClearButton = true,
                    onClearInputClick = {},
                    onSearch = onSearchMock
                )
            }
        }

        composeTestRule.onNodeWithText("Rick").performImeAction()

        verify { onSearchMock.invoke() }
        confirmVerified(onSearchMock)
    }
}
