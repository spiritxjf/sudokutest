package com.tct.mysudoku

class Cell {
    private var mRow:Int = -1
    private var mColumn:Int = -1
    private var mValue:Int = 0
    private var mIsEditAble = true

    constructor(row:Int, col:Int)
    {
        mRow = row
        mColumn = col
    }

    constructor(row:Int, col:Int, flag:Boolean)
    {
        mRow = row
        mColumn = col
        mIsEditAble = flag
    }
    constructor()
    {
        mRow = -1
        mColumn = -1
    }

    var cellValue: Int
        get() = mValue
        set(value) {
            mValue = value
        }

    var row: Int
        get() = mRow
        set(value) {
            mRow = value
        }

    var col: Int
        get() = mColumn
        set(mRow) {
            mColumn = mRow
        }

    var editable: Boolean
        get() = mIsEditAble
        set(flag) {
            mIsEditAble = flag
        }
}