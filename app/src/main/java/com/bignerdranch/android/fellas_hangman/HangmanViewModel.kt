package com.bignerdranch.android.fellas_hangman

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "HangmanViewModel"
const val CURRENT_WORD_INDEX_KEY = "CURRENT_WORD_INDEX_KEY"
const val ATTEMPTS_LEFT_KEY = "ATTEMPTS_LEFT_KEY"
const val HINTS_LEFT_KEY = "HINTS_LEFT_KEY"

class HangmanViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val letterButtonsStateKeyPrefix = "LETTER_BUTTON_STATE_"
    private var guessedLetters: MutableList<Char> = mutableListOf()
    private var correctlyGuessedLetters: MutableList<Char> = mutableListOf()
    private var currentWordIndex: Int
        get() = savedStateHandle.get(CURRENT_WORD_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_WORD_INDEX_KEY, value)

    private var wordBank = listOf(
        "PIZZA",
        "FOOTBALL"
    )

    private var hintBank = listOf(
        "An American food!",
        "An American sport!"
    )
    private var wordSelected: String
        get() = wordBank[currentWordIndex]
        set(value) {}

    private var hintSelected: String
        get() = hintBank[currentWordIndex]
        set(value) {}

    private val maxAttempts = 6
    private var attemptsLeft: Int
        get() = savedStateHandle.get(ATTEMPTS_LEFT_KEY) ?: maxAttempts
        set(value) = savedStateHandle.set(ATTEMPTS_LEFT_KEY, value)

    private var hintsLeft: Int
        get() = savedStateHandle.get(HINTS_LEFT_KEY) ?: 3
        set(value) = savedStateHandle.set(HINTS_LEFT_KEY, value)

    init {
        val letterButtons = ('A'..'Z').map { it.toString() }
        letterButtons.forEach { letter ->
            if (!savedStateHandle.contains(getButtonStateKey(letter))) {
                savedStateHandle.set(getButtonStateKey(letter), false)
            }
        }
        // Retrieve the saved state for the current word index, attempts left, and hints left
        if (!savedStateHandle.contains(CURRENT_WORD_INDEX_KEY)) {
            savedStateHandle.set(CURRENT_WORD_INDEX_KEY, 0)
        }
        if (!savedStateHandle.contains(ATTEMPTS_LEFT_KEY)) {
            savedStateHandle.set(ATTEMPTS_LEFT_KEY, maxAttempts)
        }
        if (!savedStateHandle.contains(HINTS_LEFT_KEY)) {
            savedStateHandle.set(HINTS_LEFT_KEY, 3)
        }
    }

    fun setButtonState(letter: String, isEnabled: Boolean) {
        savedStateHandle.set(getButtonStateKey(letter), isEnabled)
    }

    fun getButtonState(letter: String): Boolean {
        return savedStateHandle.get<Boolean>(getButtonStateKey(letter)) ?: false
    }

    private fun getButtonStateKey(letter: String): String {
        return "$letterButtonsStateKeyPrefix$letter"
    }
    fun hintNumber():Int {
        return (hintsLeft)
    }

    fun hintOne():String {
        return (hintSelected)
    }

    fun lowerHintNumber(){
        hintsLeft--
    }

    fun guessLetter(letter: Char) {
        if (!guessedLetters.contains(letter)) {
            guessedLetters.add(letter)
            if (!wordSelected.contains(letter)) {
                attemptsLeft--
            } else {
                correctlyGuessedLetters.add(letter)
            }
        }
    }

    //Will be used to present correct hangman Image
    fun hangManStage(): Int {
        return (6- attemptsLeft)
    }

    fun getWordSelectedValue(): String {
        return wordSelected
    }

    fun getWordDisplay(): String {
        return wordSelected.map { if (correctlyGuessedLetters.contains(it)) it else '_' }.joinToString(" ")
    }

    fun getCurrentAttemptsLeft(): Int {
        return attemptsLeft
    }

    fun decreaseAttemptsLeft(){
        attemptsLeft--
    }


    fun isGameWon(): Boolean {
        return wordSelected.all { correctlyGuessedLetters.contains(it) }
    }

    fun isGameLost(): Boolean {
        return attemptsLeft == 0
    }

    fun newGame() {
        currentWordIndex = (currentWordIndex + 1) % wordBank.size
        guessedLetters.clear()
        correctlyGuessedLetters.clear()
        attemptsLeft = maxAttempts
        hintsLeft = 3
    }

}