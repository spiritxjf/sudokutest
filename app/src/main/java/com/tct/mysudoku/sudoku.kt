package com.tct.mysudoku

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sudoku.*
import android.view.View

class sudoku : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)

        // Example of a call to a native method
        //sample_text.text = stringFromJNI()
    }

    fun setCellValue(value:Int)
    {
        val SudokuBoardView:SudokuBoardView = findViewById(R.id.sudoku_board)
        SudokuBoardView.setCellValue(value)
        //
    }
    fun onNumberClick(v: View)
    {
        val id = v.id;
        val SudokuBoardView:SudokuBoardView = findViewById(R.id.sudoku_board)

        when  (id){
            R.id.button_01 -> SudokuBoardView.setCellValue(1)
            R.id.button_02 -> SudokuBoardView.setCellValue(2)
            R.id.button_03 -> SudokuBoardView.setCellValue(3)
            R.id.button_04 -> SudokuBoardView.setCellValue(4)
            R.id.button_05 -> SudokuBoardView.setCellValue(5)
            R.id.button_06 -> SudokuBoardView.setCellValue(6)
            R.id.button_07 -> SudokuBoardView.setCellValue(7)
            R.id.button_08 -> SudokuBoardView.setCellValue(8)
            R.id.button_09 -> SudokuBoardView.setCellValue(9)
            R.id.button_lock -> SudokuBoardView.setCellLock(1)
            R.id.button_unlock -> SudokuBoardView.setCellLock(0)
            R.id.button_reset -> SudokuBoardView.resetCells()
            R.id.button_resetunlock -> SudokuBoardView.resetUnlockCells()
        }
    }

    override fun finish() {
        //super.finish()
        moveTaskToBack(true);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
