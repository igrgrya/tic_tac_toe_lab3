package com.example.tictactoelab_3_4

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.view.View.GONE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoelab_3_4.R.drawable.ic_o
import com.example.tictactoelab_3_4.databinding.ActivityGameBinding
import kotlin.properties.Delegates

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private lateinit var gameField: Array<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)

        binding.btnFinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity((intent))
        }

        binding.ivGameCell11.setOnClickListener {
            makeStepOfUser(0,0)
        }

        binding.ivGameCell12.setOnClickListener {
            makeStepOfUser(0,1)
        }

        binding.ivGameCell13.setOnClickListener {
            makeStepOfUser(0,2)
        }

        binding.ivGameCell21.setOnClickListener {
            makeStepOfUser(1,0)
        }

        binding.ivGameCell22.setOnClickListener {
            makeStepOfUser(1,1)
        }

        binding.ivGameCell23.setOnClickListener {
            makeStepOfUser(1,2)
        }

        binding.ivGameCell31.setOnClickListener {
            makeStepOfUser(2,0)
        }

        binding.ivGameCell32.setOnClickListener {
            makeStepOfUser(2,1)
        }

        binding.ivGameCell33.setOnClickListener {
            makeStepOfUser(2,2)
        }

        setContentView(binding.root)

        initGameFlied()

    }
    private fun initGameFlied(){
        gameField = Array(3){Array(3){" "}}
        binding.ivWhoseTurn.setSrc(R.drawable.ic_x)
    }

    private fun makeStep(row: Int, column: Int, symbol: String){
        gameField[row][column] = symbol
        makeStepUI("$row$column", symbol)
    }

    private fun makeStepUI(position: String, symbol: String){
        val resId = when(symbol){
            "X" -> R.drawable.ic_x
            "0" -> ic_o
            else -> return
        }

        when(position){
            "00" -> binding.ivGameCell11.setImageResource(resId)
            "01" -> binding.ivGameCell12.setImageResource(resId)
            "02" -> binding.ivGameCell13.setImageResource(resId)
            "10" -> binding.ivGameCell21.setImageResource(resId)
            "11" -> binding.ivGameCell22.setImageResource(resId)
            "12" -> binding.ivGameCell23.setImageResource(resId)
            "20" -> binding.ivGameCell31.setImageResource(resId)
            "21" -> binding.ivGameCell32.setImageResource(resId)
            "22" -> binding.ivGameCell33.setImageResource(resId)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun makeStepOfUser(row: Int, column: Int){
        if(isEmptyField(row, column)) {
            if (binding.ivWhoseTurn.isSrc(R.drawable.ic_x)) {
                makeStep(row, column, "X")
                if(checkGameField(row, column, "X").status){
                    showGameStatus(PLAYER_X_WIN)
                    return
                } else
                    binding.ivWhoseTurn.setSrc(R.drawable.ic_o)
            } else{
                makeStep(row, column, "0")
                if(checkGameField(row, column, "0").status){
                    showGameStatus(PLAYER_0_WIN)
                    return
                } else
                binding.ivWhoseTurn.setSrc(R.drawable.ic_x)
            }

            if(isFilledGameField()) showGameStatus(DRAW)


        }
        else Toast.makeText(this,"Игровое поле уже занято", Toast.LENGTH_SHORT).show()
    }

    private fun isEmptyField(row: Int, column: Int): Boolean{
        return gameField[row][column] == " "
    }

    private fun ImageView.isSrc(resId: Int): Boolean {
        return this.getTag(R.id.image_src_tag) == resId
    }

    private fun ImageView.setSrc(resId: Int) {
        this.setImageResource(resId)
        this.setTag(R.id.image_src_tag, resId)
    }

    private fun checkGameField(x: Int, y: Int, symbol: String): StatusInfo{
        var row = 0
        var column = 0
        var leftDiagonal = 0
        var rightDiagonal = 0
        var n = gameField.size

        for (i in 0..2){
            if (gameField[x][i] == symbol)
                column++
            if(gameField[i][y] == symbol)
                row++
            if(gameField[i][i] == symbol)
                leftDiagonal++
            if(gameField[i][n-i-1] == symbol)
                rightDiagonal++
        }
        return if (column == n || row == n || leftDiagonal == n || rightDiagonal == n)
            StatusInfo(true, symbol)
        else
            StatusInfo(false, "")
    }

    data class StatusInfo(val status: Boolean, val side: String)

    private fun showGameStatus(status: Int){
        val dialog = Dialog(this, R.style.Theme_TicTacToeLab_3_4)
        with(dialog){
            window?.setBackgroundDrawable(ColorDrawable(Color.argb(50,0,0,0)))
            setContentView(R.layout.dialog_popup_statusgame)
            setCancelable(true)
        }

        val image = dialog.findViewById<ImageView>(R.id.iv_dialog_x_o_who_won)
        val text = dialog.findViewById<TextView>(R.id.tv_win_draw)
        val button = dialog.findViewById<Button>(R.id.btn_dialog_ok)

        button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity((intent))
        }

        when(status){
            PLAYER_0_WIN -> {
                image.setImageResource(R.drawable.ic_o)
                text.text = "won!"
            }
            PLAYER_X_WIN -> {
                image.setImageResource(R.drawable.ic_x)
                text.text = "won!"
            }
            DRAW -> {
                image.visibility = GONE
                text.text = "draw!"
            }
        }

        dialog.show()
    }


    private fun isFilledGameField(): Boolean {
        gameField.forEach { strings ->
            if (strings.find { it == " " } != null) return false
        }
        return true
    }



    companion object{
        const val PLAYER_0_WIN = 1
        const val PLAYER_X_WIN = 2
        const val DRAW = 3
    }
}