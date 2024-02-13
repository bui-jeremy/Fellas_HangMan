package com.bignerdranch.android.fellas_hangman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var wordGuessesDisplay : TextView
    private lateinit var newGameBtn : Button
    private lateinit var gameResultDisplay : TextView
    //Image that holds our hangman
    private lateinit var hangManImages: ImageView
    private var acceptInput = true
    private val hangmanViewModel: HangmanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordGuessesDisplay = findViewById(R.id.letterGuesses)
        newGameBtn = findViewById(R.id.newGame)
        gameResultDisplay = findViewById(R.id.gameStatus)
        hangManImages = findViewById(R.id.imageofHangman)

        val letterButtons = listOf<Button>(
            findViewById(R.id.buttonA),
            findViewById(R.id.buttonB),
            findViewById(R.id.buttonC),
            findViewById(R.id.buttonD),
            findViewById(R.id.buttonE),
            findViewById(R.id.buttonF),
            findViewById(R.id.buttonG),
            findViewById(R.id.buttonH),
            findViewById(R.id.buttonI),
            findViewById(R.id.buttonJ),
            findViewById(R.id.buttonK),
            findViewById(R.id.buttonL),
            findViewById(R.id.buttonM),
            findViewById(R.id.buttonN),
            findViewById(R.id.buttonO),
            findViewById(R.id.buttonP),
            findViewById(R.id.buttonQ),
            findViewById(R.id.buttonR),
            findViewById(R.id.buttonS),
            findViewById(R.id.buttonT),
            findViewById(R.id.buttonU),
            findViewById(R.id.buttonV),
            findViewById(R.id.buttonW),
            findViewById(R.id.buttonX),
            findViewById(R.id.buttonY),
            findViewById(R.id.buttonZ)
        )

        letterButtons.forEach { button ->
            button.setOnClickListener {

                if (acceptInput == false) {
                    println("Block button pressing since the game is over!")
                } else {
                    val letter = button.text
                    println(letter.single())
                    hangmanViewModel.guessLetter(letter.single())
                    button.isEnabled = false
                    updateUI()
                }
            }
        }

        newGameBtn.isEnabled = true
        updateUI()

        newGameBtn.setOnClickListener {
            hangmanViewModel.newGame()
            letterButtons.forEach {button -> button.isEnabled = true}
            gameResultDisplay.text = ""
            acceptInput = true
            updateUI()
        }
    }

    private fun updateUI() {
        wordGuessesDisplay.text = hangmanViewModel.getWordDisplay()
        println(hangmanViewModel.getWordDisplay())
        if (hangmanViewModel.isGameWon()) {
            gameResultDisplay.text =
                getString(R.string.win_res, hangmanViewModel.getWordSelectedValue())
            newGameBtn.isEnabled = true
            acceptInput = false

        } else if (hangmanViewModel.isGameLost()) {
            gameResultDisplay.text =
                getString(R.string.lose_res, hangmanViewModel.getWordSelectedValue())
            newGameBtn.isEnabled = true
            acceptInput = false
        } else {
            newGameBtn.isEnabled = true
        }

        //Control hangman Image here:
        val hangManStage = hangmanViewModel.hangManStage()
        if (hangManStage == 0) {
            hangManImages.setImageResource(R.drawable.hangman0)
        }
        if (hangManStage == 1) {
            hangManImages.setImageResource(R.drawable.hangman1)
        }
        if (hangManStage == 2) {
            hangManImages.setImageResource(R.drawable.hangman2)
        }
        if (hangManStage == 3) {
            hangManImages.setImageResource(R.drawable.hangman3)
        }
        if (hangManStage == 4) {
            hangManImages.setImageResource(R.drawable.hangman4)
        }
        if (hangManStage == 5) {
            hangManImages.setImageResource(R.drawable.hangman5)
        }
        if (hangManStage == 6) {
            hangManImages.setImageResource(R.drawable.hangman6)
        }

    }




}