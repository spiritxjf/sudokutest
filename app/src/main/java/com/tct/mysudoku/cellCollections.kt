package com.tct.mysudoku

class cellCollecions{
    private var sudoku_size:Int = 9;
    private var mCurrentCell:Cell = Cell(-1, -1)
    private var mCells: Array<Array<Cell>> = Array(9, {Array(9,{Cell()})})

    public fun isEditable(row:Int, col:Int):Boolean{
        return mCells[row][col].editable
    }
    public fun setEditable(row:Int, col:Int, value:Boolean){
        if (mCells[row][col].cellValue > 0) {
            mCells[row][col].editable = value
        }
    }

    public fun setCellValue(row:Int, col:Int, value:Int)
    {
        mCells[row][col].cellValue = value
    }

    public fun getCellValue(row:Int, col:Int):Int
    {
        return mCells[row][col].cellValue
    }

    public fun resetCells()
    {
        val i:Int = 0
        val j:Int = 0
        for (i in 0..8)
        {
            for (j in 0..8) {
                mCells[i][j].editable = true
                mCells[i][j].cellValue = 0
            }
        }
    }
    public fun resetUnlockCells()
    {
        val i:Int = 0
        val j:Int = 0
        for (i in 0..8)
        {
            for (j in 0..8) {
                if (mCells[i][j].editable) {
                    mCells[i][j].cellValue = 0
                }
            }
        }
    }

    var cellsSize: Int
        get() = sudoku_size
        set(value) {
            sudoku_size = value
        }
}