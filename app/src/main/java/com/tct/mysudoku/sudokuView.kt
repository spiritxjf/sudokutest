package com.tct.mysudoku

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
/**
 * Sudoku board widget.
 *
 * @author romario
 */
class SudokuBoardView
//	public SudokuBoardView(Context context, AttributeSet attrs) {
//		this(context, attrs, R.attr.sudokuBoardViewStyle);
//	}

// TODO: do I need an defStyle?
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null/*, int defStyle*/) : View(context, attrs) {
    private var mCellWidth: Float = 0.toFloat()
    private var mCellHeight: Float = 0.toFloat()

    private val mLinePaint: Paint
    private val mSectorLinePaint: Paint
    private val mCellValuePaint: Paint
    private val mCellValueReadonlyPaint: Paint
    private val mCellNotePaint: Paint
    private var mNumberLeft: Int = 0
    private var mNumberTop: Int = 0
    private var mNoteTop: Float = 0.toFloat()
    private var mSectorLineWidth: Int = 0
    private val mBackgroundColorSecondary: Paint
    private val mBackgroundColorReadOnly: Paint
    private val mBackgroundColorTouched: Paint
    private val mBackgroundColorSelected: Paint
    private val mBackgroundColorHighlighted: Paint

    private var mCellValueInvalidPaint: Paint
    private var mCell:Cell = Cell(-1,-1)
    private var mCellCollecions:cellCollecions = cellCollecions()
    private var mValue: Int = 0
    public val conflict_flag:Int = 1;
    public val lock_flag:Int = 2;

    var lineColor: Int
        get() = mLinePaint.color
        set(color) {
            mLinePaint.color = color
        }

    var sectorLineColor: Int
        get() = mSectorLinePaint.color
        set(color) {
            mSectorLinePaint.color = color
        }

    var textColor: Int
        get() = mCellValuePaint.color
        set(color) {
            mCellValuePaint.color = color
        }

    var textColorReadOnly: Int
        get() = mCellValueReadonlyPaint.color
        set(color) {
            mCellValueReadonlyPaint.color = color
        }

    var textColorNote: Int
        get() = mCellNotePaint.color
        set(color) {
            mCellNotePaint.color = color
        }

    var backgroundColorSecondary: Int
        get() = mBackgroundColorSecondary.color
        set(color) {
            mBackgroundColorSecondary.color = color
        }

    var backgroundColorReadOnly: Int
        get() = mBackgroundColorReadOnly.color
        set(color) {
            mBackgroundColorReadOnly.color = color
        }

    var backgroundColorTouched: Int
        get() = mBackgroundColorTouched.color
        set(color) {
            mBackgroundColorTouched.color = color
        }

    var backgroundColorSelected: Int
        get() = mBackgroundColorSelected.color
        set(color) {
            mBackgroundColorSelected.color = color
        }

    var backgroundColorHighlighted: Int
        get() = mBackgroundColorHighlighted.color
        set(color) {
            mBackgroundColorHighlighted.color = color
        }

    init {

        isFocusable = true
        isFocusableInTouchMode = true

        mLinePaint = Paint()
        mSectorLinePaint = Paint()
        mCellValuePaint = Paint()
        mCellValueReadonlyPaint = Paint()
        mCellValueInvalidPaint = Paint()
        mCellNotePaint = Paint()
        mBackgroundColorSecondary = Paint()
        mBackgroundColorReadOnly = Paint()
        mBackgroundColorTouched = Paint()
        mBackgroundColorSelected = Paint()
        mBackgroundColorHighlighted = Paint()

        mCellValuePaint.isAntiAlias = true
        mCellValueReadonlyPaint.isAntiAlias = true
        mCellValueInvalidPaint.isAntiAlias = true
        mCellNotePaint.isAntiAlias = true
        mCellValueInvalidPaint.color = Color.RED

        val a = context.obtainStyledAttributes(attrs, R.styleable.SudokuBoardView/*, defStyle, 0*/)

        lineColor = a.getColor(R.styleable.SudokuBoardView_lineColor, Color.BLACK)
        sectorLineColor = a.getColor(R.styleable.SudokuBoardView_sectorLineColor, Color.BLACK)
        textColor = a.getColor(R.styleable.SudokuBoardView_textColor, Color.BLACK)
        textColorReadOnly = a.getColor(R.styleable.SudokuBoardView_textColorReadOnly, Color.RED)
        textColorNote = a.getColor(R.styleable.SudokuBoardView_textColorNote, Color.BLACK)
        setBackgroundColor(a.getColor(R.styleable.SudokuBoardView_backgroundColor, Color.WHITE))
        backgroundColorSecondary = a.getColor(R.styleable.SudokuBoardView_backgroundColorSecondary, NO_COLOR)
        backgroundColorReadOnly = a.getColor(R.styleable.SudokuBoardView_backgroundColorReadOnly, NO_COLOR)
        backgroundColorTouched = a.getColor(R.styleable.SudokuBoardView_backgroundColorTouched, Color.YELLOW)
        backgroundColorSelected = a.getColor(R.styleable.SudokuBoardView_backgroundColorSelected, Color.YELLOW)
        backgroundColorHighlighted = a.getColor(R.styleable.SudokuBoardView_backgroundColorHighlighted, Color.GREEN)

        a.recycle()
    }/*, defStyle*/

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)


        //        Log.d(TAG, "widthMode=" + getMeasureSpecModeString(widthMode));
        //        Log.d(TAG, "widthSize=" + widthSize);
        //        Log.d(TAG, "heightMode=" + getMeasureSpecModeString(heightMode));
        //        Log.d(TAG, "heightSize=" + heightSize);

        var width = -1
        var height = -1
        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize
        } else {
            width = DEFAULT_BOARD_SIZE
            if (widthMode == View.MeasureSpec.AT_MOST && width > widthSize) {
                width = widthSize
            }
        }
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize
        } else {
            height = DEFAULT_BOARD_SIZE
            if (heightMode == View.MeasureSpec.AT_MOST && height > heightSize) {
                height = heightSize
            }
        }

        if (widthMode != View.MeasureSpec.EXACTLY) {
            width = height
        }

        if (heightMode != View.MeasureSpec.EXACTLY) {
            height = width
        }

        if (widthMode == View.MeasureSpec.AT_MOST && width > widthSize) {
            width = widthSize
        }
        if (heightMode == View.MeasureSpec.AT_MOST && height > heightSize) {
            height = heightSize
        }

        mCellWidth = (width - paddingLeft - paddingRight) / 9.0f
        mCellHeight = (height - paddingTop - paddingBottom) / 9.0f

        setMeasuredDimension(width, height)

        val cellTextSize = mCellHeight * 0.75f
        mCellValuePaint.textSize = cellTextSize
        mCellValueReadonlyPaint.textSize = cellTextSize
        mCellValueInvalidPaint.textSize = cellTextSize
        mCellNotePaint.textSize = mCellHeight / 3.0f
        // compute offsets in each cell to center the rendered number
        mNumberLeft = ((mCellWidth - mCellValuePaint.measureText("9")) / 2).toInt()
        mNumberTop = ((mCellHeight - mCellValuePaint.textSize) / 2).toInt()

        // add some offset because in some resolutions notes are cut-off in the top
        mNoteTop = mCellHeight / 50.0f

        computeSectorLineWidth(width, height)
    }

    private fun computeSectorLineWidth(widthInPx: Int, heightInPx: Int) {
        val sizeInPx = if (widthInPx < heightInPx) widthInPx else heightInPx
        val dipScale = context.resources.displayMetrics.density
        val sizeInDip = sizeInPx / dipScale

        var sectorLineWidthInDip = 2.0f

        if (sizeInDip > 150) {
            sectorLineWidthInDip = 3.0f
        }

        mSectorLineWidth = (sectorLineWidthInDip * dipScale).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // some notes:
        // Drawable has its own draw() method that takes your Canvas as an arguement

        // TODO: I don't get this, why do I need to substract padding only from one side?
        val width = width - paddingRight
        val height = height - paddingBottom

        val paddingLeft = paddingLeft
        val paddingTop = paddingTop

        // draw secondary background
        if (mBackgroundColorSecondary.color != NO_COLOR) {
            canvas.drawRect(3 * mCellWidth, 0f, 6 * mCellWidth, 3 * mCellWidth, mBackgroundColorSecondary)
            canvas.drawRect(0f, 3 * mCellWidth, 3 * mCellWidth, 6 * mCellWidth, mBackgroundColorSecondary)
            canvas.drawRect(6 * mCellWidth, 3 * mCellWidth, 9 * mCellWidth, 6 * mCellWidth, mBackgroundColorSecondary)
            canvas.drawRect(3 * mCellWidth, 6 * mCellWidth, 6 * mCellWidth, 9 * mCellWidth, mBackgroundColorSecondary)
        }

        var selectedValue = 0
        if (mCell.row >= 0 && mCell.col >= 0) {
            selectedValue = mCellCollecions.getCellValue(mCell.row,mCell.col)
        }


        for (row in 0..8) {
            for (col in 0..8) {
                var cellLeft:Int = Math.round(col * mCellWidth + paddingLeft)
                var cellTop: Int = Math.round(row * mCellHeight + paddingTop)

                // draw read-only field background
                if (!mCellCollecions.isEditable(row,col) && (selectedValue > 0)) {
                    if (mBackgroundColorReadOnly.color != NO_COLOR) {
                        canvas.drawRect(
                                cellLeft.toFloat(), cellTop.toFloat(),
                                cellLeft + mCellWidth, cellTop + mCellHeight,
                                mBackgroundColorReadOnly)
                    }
                }

                // highlight similar cells
                if (selectedValue != 0 && selectedValue == mCellCollecions.getCellValue(row,col) &&
                        (mCell.row != row && mCell.col != col)) {
                    if (mBackgroundColorHighlighted.color != NO_COLOR) {
                        canvas.drawRect(
                                cellLeft.toFloat(), cellTop.toFloat(),
                                cellLeft + mCellWidth, cellTop + mCellHeight,
                                mBackgroundColorHighlighted)
                    }
                }
            }
        }

        if(mCell.row >= 0 && mCell.col >= 0)
        {
            val cellLeft = Math.round(mCell.col * mCellWidth) + paddingLeft
            val cellTop = Math.round( mCell.row * mCellHeight) + paddingTop
            canvas.drawRect(
                    cellLeft.toFloat(), paddingTop.toFloat(),
                    cellLeft + mCellWidth, height.toFloat(),
                    mBackgroundColorTouched)
            canvas.drawRect(
                    paddingLeft.toFloat(), cellTop.toFloat(),
                    width.toFloat(), cellTop + mCellHeight,
                    mBackgroundColorTouched)
        }

        for (row in 0..8) {
            for (col in 0..8) {
                if (mCellCollecions.getCellValue(row, col) > 0) {

                    var cellValuePaint = if (mCellCollecions.isEditable(row, col)) mCellValuePaint else mCellValueReadonlyPaint
                    canvas.drawText(Integer.toString(mCellCollecions.getCellValue(row, col)), col * mCellWidth + mCellWidth / 4, (row + 1) * mCellHeight - mCellHeight / 4, cellValuePaint)
                }
            }
        }
        // draw vertical lines
        for (c in 0..9) {
            val x = c * mCellWidth + paddingLeft
            canvas.drawLine(x, paddingTop.toFloat(), x, height.toFloat(), mLinePaint)
        }

        // draw horizontal lines
        for (r in 0..9) {
            val y = r * mCellHeight + paddingTop
            canvas.drawLine(paddingLeft.toFloat(), y, width.toFloat(), y, mLinePaint)
        }

        val sectorLineWidth1 = mSectorLineWidth / 2
        val sectorLineWidth2 = sectorLineWidth1 + mSectorLineWidth % 2

        // draw sector (thick) lines
        var c = 0
        while (c <= 9) {
            val x = c * mCellWidth + paddingLeft
            canvas.drawRect(x - sectorLineWidth1, paddingTop.toFloat(), x + sectorLineWidth2, height.toFloat(), mSectorLinePaint)
            c = c + 3
        }

        var r = 0
        while (r <= 9) {
            val y = r * mCellHeight + paddingTop
            canvas.drawRect(paddingLeft.toFloat(), y - sectorLineWidth1, width.toFloat(), y + sectorLineWidth2, mSectorLinePaint)
            r = r + 3
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.getX().toInt()
        val y = event!!.getY().toInt()

        val cell = getCellAtPoint(x, y)

        if (event.action == MotionEvent.ACTION_DOWN) {
            mCell.col = cell!!.col
            mCell.row = cell!!.row
            invalidate()
        }

        return super.onTouchEvent(event)
    }

    private fun getCellAtPoint(x: Int, y: Int): Cell? {
        // take into account padding
        val lx = x - paddingLeft
        val ly = y - paddingTop

        val row = (ly / mCellHeight).toInt()
        val col = (lx / mCellWidth).toInt()

        return if (col >= 0 && col < 9
                && row >= 0 && row < 9) {
            Cell(row, col)
        } else {
            null
        }
    }


    public fun setCellValue(value:Int)
    {
        if (mCellCollecions.isEditable(mCell.row, mCell.col)) {
            //mCell.row  mCell.col value
            //mValue = value
            mCellCollecions.setCellValue(mCell.row, mCell.col, value)
            invalidate()
        }
    }

    public fun setCellValue(value:String)
    {
        mCellCollecions.resetCells()
        for (i in 0..80)
        {
            //mCell.row  mCell.col value
            //mValue = value
            mCellCollecions.setCellValue(i/9,i%9, value[i] - '0')
            mCellCollecions.setEditable(i/9,i%9,false)
            invalidate()
        }
    }

    public fun setCellLock(value:Boolean)
    {
        if (mCell.row >= 0 && mCell.col >= 0) {
            mCellCollecions.setEditable(mCell.row, mCell.col, !value)
            invalidate()
        }
    }

    public fun resetCells()
    {
        mCellCollecions.resetCells()
        invalidate()
    }

    public fun resetUnlockCells()
    {
        mCellCollecions.resetUnlockCells()
        invalidate()
    }
    companion object {

        val DEFAULT_BOARD_SIZE = 100

        /**
         * "Color not set" value. (In relation to [Color], it is in fact black color with
         * alpha channel set to 0 => that means it is completely transparent).
         */
        private val NO_COLOR = 0
    }

}
