package com.tct.mysudoku

import android.util.Log

class cellCollecions{
    private var sudoku_size:Int = 9;
    private var mCurrentCell:Cell = Cell(-1, -1)
    private var mCells: Array<Array<Cell>> = Array(9, {Array(9,{Cell()})})

    fun isEditable(row:Int, col:Int):Boolean{
        return mCells[row][col].editable
    }
    fun setEditable(row:Int, col:Int, value:Boolean){
        if (mCells[row][col].cellValue > 0) {
            mCells[row][col].editable = value
        }
        else
        {
            mCells[row][col].editable = true
        }
    }

    fun setCellValue(row:Int, col:Int, value:Int)
    {
        mCells[row][col].cellValue = value
        isConflict(row, col)
    }

    fun getCellValue(row:Int, col:Int):Int
    {
        return mCells[row][col].cellValue
    }

    fun getCellConflict(row:Int, col:Int):Boolean
    {
        return mCells[row][col].isConflict
    }

    fun resetCells()
    {
        for (i in 0..8)
        {
            for (j in 0..8) {
                mCells[i][j].editable = true
                mCells[i][j].cellValue = 0
                mCells[i][j].isConflict = false
            }
        }
    }
    fun resetUnlockCells()
    {
        for (i in 0..8)
        {
            for (j in 0..8) {
                if (mCells[i][j].editable) {
                    mCells[i][j].cellValue = 0
                }
            }
        }
        isConflict(0,0)
        isConflict(1,3)
        isConflict(2,6)
        isConflict(3,1)
        isConflict(4,4)
        isConflict(5,7)
        isConflict(6,2)
        isConflict(7,5)
        isConflict(8,8)
    }

    private fun isConflict(row: Int, col: Int)
    {
        var startrow = (row / 3) * 3
        var startcol = (col / 3) * 3

        for (i in 0..8)
        {
            mCells[row][i].isConflict = false
        }
        for (i in 0..8)
        {
            mCells[i][col].isConflict = false
        }
        for (i in 0..8)
        {
            mCells[startrow + i % 3][startcol + i / 3].isConflict = false
        }

        //conflict in row
        for (i in 0..8)
        {
            if (mCells[row][i].cellValue == 0)
            {
                continue
            }
            for (j in i+1..8) {
                if (mCells[row][i].cellValue == mCells[row][j].cellValue) {
                    mCells[row][i].isConflict = true;
                    mCells[row][j].isConflict = true
                }
            }
        }

        //conflict in col
        for (i in 0..8)
        {
            if (mCells[i][col].cellValue == 0)
            {
                continue
            }
            for (j in i+1..8) {
                if (mCells[i][col].cellValue == mCells[j][col].cellValue) {
                    mCells[i][col].isConflict = true
                    mCells[j][col].isConflict = true
                }
            }
        }

        //conflict in cellgroup 3*3
        for (i in 0..8)
        {
            if (mCells[startrow + i / 3][startcol + i % 3].cellValue == 0)
            {
                continue
            }
            for (j in i+1..8) {
                if (mCells[startrow + i / 3][startcol + i % 3].cellValue == mCells[startrow + j / 3][startcol + j % 3].cellValue)
                {
                    mCells[startrow + i / 3][startcol + i % 3].isConflict = true
                    mCells[startrow + j / 3][startcol + j % 3].isConflict = true
                }
            }
        }

        Log.d("sudokutest", "is conflict = " + mCells[row][col].isConflict)
    }

    var cellsSize: Int
        get() = sudoku_size
        set(value) {
            sudoku_size = value
        }
}