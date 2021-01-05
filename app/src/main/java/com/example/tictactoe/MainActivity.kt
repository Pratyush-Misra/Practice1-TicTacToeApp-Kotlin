package com.example.tictactoe

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var board : Array<Array<Button>>
    var PLAYER : Boolean = true       // i.e. player X turn starts
    var TURN_CNT : Int = 0
    var boardStatus = Array(3){IntArray(3)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = arrayOf(
                arrayOf(btn1,btn2,btn3),
                arrayOf(btn4,btn5,btn6),
                arrayOf(btn7,btn8,btn9)
        )

        for(i in board){
            for(eachBtn in i){
                eachBtn.setOnClickListener(this)
            }
        }

        initializeBoardStatus()

        reset.setOnClickListener(){
            TURN_CNT = 0
            PLAYER = true   // i.e. player X turn starts
            displayTurn.text = "Player X Turn"
            initializeBoardStatus()
        }
    }

    private fun initializeBoardStatus() {
        for(i in 0..2){
            for(j in 0..2){
                boardStatus[i][j] = -1
                board[i][j].isEnabled = true
                board[i][j].text=""
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn1 -> updateValue(row = 0, col = 0, playerTurn = PLAYER)
            R.id.btn2 -> updateValue(row = 0, col = 1, playerTurn = PLAYER)
            R.id.btn3 -> updateValue(row = 0, col = 2, playerTurn = PLAYER)
            R.id.btn4 -> updateValue(row = 1, col = 0, playerTurn = PLAYER)
            R.id.btn5 -> updateValue(row = 1, col = 1, playerTurn = PLAYER)
            R.id.btn6 -> updateValue(row = 1, col = 2, playerTurn = PLAYER)
            R.id.btn7 -> updateValue(row = 2, col = 0, playerTurn = PLAYER)
            R.id.btn8 -> updateValue(row = 2, col = 1, playerTurn = PLAYER)
            R.id.btn9 -> updateValue(row = 2, col = 2, playerTurn = PLAYER)
        }

        checkWinner()

        var txt:String =              // update the Top Display i.e. Player Turn
                if(TURN_CNT == 9)    "Game Draw"
                else if(PLAYER)      "Player X Turn"
                else "Player O Turn"
        if(displayTurn.text.contains("Winner") == false)    updateDisplay(txt)
    }

    private fun updateValue(row: Int, col: Int, playerTurn: Boolean) {
        board[row][col].apply{
            isEnabled = false;
            text = if(playerTurn)   "X"     else "O"
        }
        boardStatus[row][col] = if(playerTurn)  1   else  0
        TURN_CNT++
        PLAYER = !PLAYER
    }

    private fun checkWinner() {
        // for rows
        for(i in 0..2){
            if(boardStatus[i][0] != -1 && boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if(boardStatus[i][0] == 1)  updateDisplay("Player X Winner")
                else    updateDisplay("Player O Winner")
                return
            }
        }

        // for columns
        for(j in 0..2){
            if(boardStatus[0][j] != -1 && boardStatus[0][j] == boardStatus[1][j] && boardStatus[0][j] == boardStatus[2][j]){
                if(boardStatus[0][j] == 1)  updateDisplay("Player X Winner")
                else    updateDisplay("Player O Winner")
                return
            }
        }

        // for diagonals
        if(boardStatus[0][0] != -1 && boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if(boardStatus[0][0] == 1) updateDisplay("Player X Winner")
            else    updateDisplay("Player O Winner")
            return
        }

        if(boardStatus[2][0] != -1 && boardStatus[2][0] == boardStatus[1][1] && boardStatus[2][0] == boardStatus[0][2]){
            if(boardStatus[2][0] == 1) updateDisplay("Player X Winner")
            else    updateDisplay("Player O Winner")
            return
        }
    }

    private fun updateDisplay(txt: String) {
        displayTurn.text = txt
        if(txt.contains("Winner"))  buttonDisable()
    }

    private fun buttonDisable() {
        for(i in board){
            for(eachBtn in i)   eachBtn.isEnabled = false
        }
    }
}
