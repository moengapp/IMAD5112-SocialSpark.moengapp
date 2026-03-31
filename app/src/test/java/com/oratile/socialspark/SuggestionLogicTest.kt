package com.oratile.socialspark

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for the Social Spark app logic.
 * These tests verify that the suggestion matching logic works correctly
 * for all valid time-of-day inputs and handles edge cases properly.
 */
class SuggestionLogicTest {

    // Helper function that replicates the getSuggestion logic for testing
    // This mirrors the when expression used in MainActivity
    private fun getSuggestion(timeOfDay: String): Pair<String, String>? {
        val normalizedInput = timeOfDay.lowercase().trim()

        return when (normalizedInput) {
            "morning" -> Pair("morning_suggestion", "☀️")
            "mid-morning", "mid morning", "midmorning" -> Pair("midmorning_suggestion", "🤝")
            "afternoon" -> Pair("afternoon_suggestion", "😂")
            "afternoon snack time", "afternoon snack", "snack time", "snack" -> Pair("snack_suggestion", "💭")
            "dinner" -> Pair("dinner_suggestion", "📞")
            "evening", "night", "after dinner" -> Pair("evening_suggestion", "💬")
            else -> null
        }
    }

    // Test: Morning input returns a valid suggestion
    @Test
    fun testMorningSuggestion() {
        val result = getSuggestion("Morning")
        assertNotNull("Morning should return a suggestion", result)
        assertEquals("☀️", result?.second)
    }

    // Test: Mid-morning variations all return valid suggestions
    @Test
    fun testMidMorningSuggestion() {
        assertNotNull("mid-morning should match", getSuggestion("mid-morning"))
        assertNotNull("mid morning should match", getSuggestion("mid morning"))
        assertNotNull("midmorning should match", getSuggestion("midmorning"))
    }

    // Test: Afternoon input returns a valid suggestion
    @Test
    fun testAfternoonSuggestion() {
        val result = getSuggestion("Afternoon")
        assertNotNull("Afternoon should return a suggestion", result)
        assertEquals("😂", result?.second)
    }

    // Test: Afternoon snack time variations return valid suggestions
    @Test
    fun testAfternoonSnackTimeSuggestion() {
        assertNotNull("afternoon snack time should match", getSuggestion("afternoon snack time"))
        assertNotNull("snack time should match", getSuggestion("snack time"))
        assertNotNull("afternoon snack should match", getSuggestion("afternoon snack"))
    }

    // Test: Dinner input returns a valid suggestion
    @Test
    fun testDinnerSuggestion() {
        val result = getSuggestion("Dinner")
        assertNotNull("Dinner should return a suggestion", result)
        assertEquals("📞", result?.second)
    }

    // Test: Evening/Night variations return valid suggestions
    @Test
    fun testEveningNightSuggestion() {
        assertNotNull("evening should match", getSuggestion("evening"))
        assertNotNull("night should match", getSuggestion("night"))
        assertNotNull("after dinner should match", getSuggestion("after dinner"))
    }

    // Test: Case insensitivity - uppercase inputs should still match
    @Test
    fun testCaseInsensitivity() {
        assertNotNull("MORNING should match", getSuggestion("MORNING"))
        assertNotNull("DINNER should match", getSuggestion("DINNER"))
        assertNotNull("Evening should match", getSuggestion("Evening"))
    }

    // Test: Invalid input returns null (triggers error handling)
    @Test
    fun testInvalidInput() {
        assertNull("Random text should return null", getSuggestion("random text"))
        assertNull("Numbers should return null", getSuggestion("12345"))
        assertNull("Empty-like input should return null", getSuggestion("hello"))
    }

    // Test: Whitespace handling - inputs with extra spaces should still match
    @Test
    fun testWhitespaceHandling() {
        assertNotNull("Input with leading spaces should match", getSuggestion("  morning  "))
        assertNotNull("Input with trailing spaces should match", getSuggestion("dinner  "))
    }

    // Test: Empty input returns null
    @Test
    fun testEmptyInput() {
        assertNull("Empty string should return null", getSuggestion(""))
    }
}
