package com.oratile.socialspark

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// MainActivity - The main entry point of the Social Spark app
// This activity handles user input for time of day and displays
// social interaction suggestions based on the input provided.
class MainActivity : AppCompatActivity() {

    // Tag used for logging throughout the activity
    private val TAG = "SocialSparkApp"

    // UI element references
    private lateinit var etTimeOfDay: EditText
    private lateinit var btnGetSuggestion: Button
    private lateinit var btnReset: Button
    private lateinit var tvSuggestion: TextView
    private lateinit var tvEmoji: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: App started successfully")

        // Initialize all UI elements by finding them from the layout
        etTimeOfDay = findViewById(R.id.etTimeOfDay)
        btnGetSuggestion = findViewById(R.id.btnGetSuggestion)
        btnReset = findViewById(R.id.btnReset)
        tvSuggestion = findViewById(R.id.tvSuggestion)
        tvEmoji = findViewById(R.id.tvEmoji)

        Log.i(TAG, "onCreate: UI elements initialized")

        // Set click listener for the "Get Suggestion" button
        // When clicked, it reads user input and displays a social spark suggestion
        btnGetSuggestion.setOnClickListener {
            val input = etTimeOfDay.text.toString().trim()
            Log.d(TAG, "btnGetSuggestion clicked with input: '$input'")
            handleInput(input)
        }

        // Set click listener for the "Reset" button
        // Clears all input fields and suggestion display
        btnReset.setOnClickListener {
            Log.d(TAG, "btnReset clicked: Clearing all fields")
            clearFields()
        }
    }

    /**
     * handleInput - Validates user input and retrieves the appropriate suggestion
     * @param input The time of day entered by the user
     *
     * This function first checks if the input is empty and shows an error if so.
     * Otherwise, it calls getSuggestion() to get the social spark suggestion
     * and displays it along with an emoji on screen.
     */
    private fun handleInput(input: String) {
        // Validate that the user has entered something
        if (input.isEmpty()) {
            Log.w(TAG, "handleInput: Empty input detected")
            tvSuggestion.text = "⚠\uFE0F Oops! Please enter a time of day to get your social spark!\n(e.g., Morning, Afternoon, Dinner)"
            tvEmoji.text = "🤔"
            tvSuggestion.setTextColor(getColor(R.color.error_red))
            return
        }

        // Get the social spark suggestion based on user input
        val result = getSuggestion(input)

        if (result != null) {
            // Valid time of day entered - display the suggestion
            Log.i(TAG, "handleInput: Valid input '$input' - showing suggestion")
            tvSuggestion.text = result.first
            tvEmoji.text = result.second
            tvSuggestion.setTextColor(getColor(R.color.suggestion_text))
        } else {
            // Invalid time of day entered - show helpful error message
            Log.w(TAG, "handleInput: Invalid input '$input' - showing error")
            tvSuggestion.text = "❌ \"$input\" is not a recognized time of day.\n\nPlease try one of these:\n• Morning\n• Mid-Morning\n• Afternoon\n• Afternoon Snack Time\n• Dinner\n• Evening / Night"
            tvEmoji.text = "😅"
            tvSuggestion.setTextColor(getColor(R.color.error_red))
        }
    }

    /**
     * getSuggestion - Uses if/when statements to determine the social spark suggestion
     * @param timeOfDay The time of day string entered by the user
     * @return A Pair of (suggestion text, emoji) or null if the input is not recognised
     *
     * This function uses a when expression (Kotlin's version of a switch statement)
     * to match the user's input to predefined social interaction suggestions.
     * The matching is case-insensitive to improve usability.
     */
    private fun getSuggestion(timeOfDay: String): Pair<String, String>? {
        // Convert input to lowercase for case-insensitive matching
        val normalizedInput = timeOfDay.lowercase().trim()
        Log.d(TAG, "getSuggestion: Normalized input = '$normalizedInput'")

        // Use when expression (if/switch statement) to match time of day
        // to the appropriate social spark suggestion
        return when (normalizedInput) {
            "morning" -> {
                Log.i(TAG, "getSuggestion: Matched 'morning'")
                Pair(
                    "🌅 Good Morning Spark!\n\nSend a \"Good morning\" text to a family member. " +
                            "A simple message can brighten their entire day and strengthen your bond!",
                    "☀\uFE0F"
                )
            }

            "mid-morning", "mid morning", "midmorning" -> {
                Log.i(TAG, "getSuggestion: Matched 'mid-morning'")
                Pair(
                    "☕ Mid-Morning Spark!\n\nReach out to a colleague with a quick \"Thank you.\" " +
                            "Recognizing someone's effort goes a long way in building positive relationships!",
                    "🤝"
                )
            }

            "afternoon" -> {
                Log.i(TAG, "getSuggestion: Matched 'afternoon'")
                Pair(
                    "🌤\uFE0F Afternoon Spark!\n\nShare a funny meme or an interesting link with a friend. " +
                            "A little humor or knowledge-sharing keeps the friendship alive and fun!",
                    "😂"
                )
            }

            "afternoon snack time", "afternoon snack", "snack time", "snack" -> {
                Log.i(TAG, "getSuggestion: Matched 'afternoon snack time'")
                Pair(
                    "🍪 Afternoon Snack Spark!\n\nSend a quick \"thinking of you\" message to someone special. " +
                            "It's a small gesture that shows you care, even during a busy day!",
                    "💭"
                )
            }

            "dinner" -> {
                Log.i(TAG, "getSuggestion: Matched 'dinner'")
                Pair(
                    "🍽\uFE0F Dinner Spark!\n\nCall a friend or relative for a 5-minute catch-up. " +
                            "Hearing someone's voice creates a deeper connection than texting ever could!",
                    "📞"
                )
            }

            "evening", "night", "after dinner" -> {
                Log.i(TAG, "getSuggestion: Matched 'evening/night'")
                Pair(
                    "🌙 Evening Spark!\n\nLeave a thoughtful comment on a friend's post. " +
                            "Engaging with their content shows you're paying attention and value their life updates!",
                    "💬"
                )
            }

            else -> {
                // No matching time of day found - return null to trigger error handling
                Log.w(TAG, "getSuggestion: No match found for '$normalizedInput'")
                null
            }
        }
    }

    /**
     * clearFields - Resets all UI elements to their default state
     * Clears the text input field and resets the suggestion and emoji displays
     */
    private fun clearFields() {
        etTimeOfDay.text.clear()
        tvSuggestion.text = "Enter a time of day above to receive your social spark suggestion! ✨"
        tvSuggestion.setTextColor(getColor(R.color.suggestion_text))
        tvEmoji.text = "💡"
        Log.i(TAG, "clearFields: All fields cleared successfully")
    }
}
