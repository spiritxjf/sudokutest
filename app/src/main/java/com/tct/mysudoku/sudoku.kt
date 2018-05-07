package com.tct.mysudoku

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sudoku.*
import android.view.View
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.tct.mysudoku.data.cellsdatabase
import java.util.*

class sudoku : AppCompatActivity() {
    var mlevel:Int = 0
    var mtemplevel:Int = 0
    val sukukudatabase: cellsdatabase = cellsdatabase()

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

    fun fillCells()
    {
        val SudokuBoardView:SudokuBoardView = findViewById(R.id.sudoku_board)
        val dataindex:Int = (Math.random() * 30).toInt() + mlevel * 30
        val data:String = sukukudatabase.getCellsData(dataindex)
        SudokuBoardView.setCellValue(data)
    }

    fun selectNewGame()
    {
        val items = arrayOf("简单", "中等", "困难")

        val dialog = AlertDialog.Builder(this)
                .setTitle("难度选择")//设置对话框的标题
                .setSingleChoiceItems(items, mlevel, DialogInterface.OnClickListener { dialog, which -> mtemplevel = which})
                .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss(); })
                .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which -> mlevel = mtemplevel;dialog.dismiss();fillCells() }).create()
        dialog.show()

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
            R.id.button_lock -> SudokuBoardView.setCellLock(true)
            R.id.button_unlock -> SudokuBoardView.setCellLock(false)
            //R.id.button_reset -> SudokuBoardView.resetCells()
            R.id.button_reset -> selectNewGame()
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
