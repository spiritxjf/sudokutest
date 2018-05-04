package com.tct.mysudoku

class cellCollecions{
    private var numberArray = Array(81,{0})
    private var numberLockArray = Array(81,{0})
    private var numberflagArray = Array(81,{0})
    private var sudoku_size:Int = 9;
    private var mCurrentCell:Cell = Cell(-1, -1)
    private var mCellArray: Set<Cell> = setOf(Cell(-1,-1))
    private var mCells: Array<Cell> = Array(81, {Cell()})

    public fun isEditable(row:Int, col:Int):Boolean{
        return numberLockArray[row * sudoku_size + col] == 0
    }
    public fun setEditable(row:Int, col:Int, value:Int){
        if (numberArray[row * sudoku_size + col] > 0) {
            numberLockArray[row * sudoku_size + col] = 1 - value
        }
    }
    public fun setCellFlag(row:Int, col:Int, value:Int)
    {
        numberflagArray[row * sudoku_size + col] = value
    }

    public fun getCellFlag(row:Int, col:Int):Int
    {
        return numberflagArray[row * sudoku_size + col]
    }

    public fun setCellValue(row:Int, col:Int, value:Int)
    {
        numberArray[row * sudoku_size + col] = value
    }

    public fun getCellValue(row:Int, col:Int):Int
    {
        return numberArray[row * sudoku_size + col]
    }

    public fun resetCells()
    {
        val i:Int = 0
        for (i in 0..80)
        {
            numberLockArray[i] = 0
            numberArray[i] = 0
        }
    }
    public fun resetUnlockCells()
    {
        val i:Int = 0
        for (i in 0..80)
        {
            if (numberLockArray[i] == 0) {
                numberArray[i] = 0
            }
        }
    }

    var cellsSize: Int
        get() = sudoku_size
        set(value) {
            sudoku_size = value
        }
}