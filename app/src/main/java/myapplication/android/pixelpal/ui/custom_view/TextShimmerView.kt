package myapplication.android.pixelpal.ui.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.BlurMaskFilter
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import myapplication.android.pixelpal.R

class TextShimmerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var mainTextView: TextView
    private lateinit var shimmerTextView: TextView

    var gravity: Int = Gravity.START
        set(value) {
            field = value
            requestLayout()
        }

    var alignment: Int = View.TEXT_ALIGNMENT_CENTER
        set(value) {
            if (value != field){
                field = value
                requestLayout()
            }
        }

    var font: Typeface? = null
        set(value) {
            field = value
            requestLayout()
        }

    var textSize = 0f
        set(value) {
            field = value
            requestLayout()
        }

    var maxLineCount = 0
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }

    var shimmerColor: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    var textColor: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    var text: String = ""

    init {
        LayoutInflater.from(context).inflate(R.layout.text_shimmer, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.TextShimmerView) {
            initVariables()
            initMainText()
            initShimmerText()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        measureChildWithMargins(mainTextView, widthMeasureSpec, 0, heightMeasureSpec, 0)
        measureChildWithMargins(shimmerTextView, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val actualWidth = mainTextView.measuredWidth + paddingLeft + paddingRight
        val actualHeight = mainTextView.measuredHeight + paddingTop + paddingBottom

        setMeasuredDimension(
            resolveSize(actualWidth, widthMeasureSpec),
            resolveSize(actualHeight, heightMeasureSpec)
        )
    }

    fun setShimmerTextAlignment(textAlignment: Int){
        mainTextView.textAlignment = textAlignment
        shimmerTextView.textAlignment = textAlignment
        alignment = textAlignment
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        onTextLayout(mainTextView)
        onTextLayout(shimmerTextView)
    }

    fun setShimmerText(text: String) {
        mainTextView.text = text
        shimmerTextView.text = text
        requestLayout()
    }

    private fun onTextLayout(textView: TextView) {
        textView.layout(
            paddingLeft,
            paddingTop,
            paddingLeft + mainTextView.measuredWidth,
            paddingTop + mainTextView.measuredHeight
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    private fun TypedArray.initVariables() {
        val fontId = getResourceId(R.styleable.TextShimmerView_android_fontFamily, 0)
        gravity = getInt(R.styleable.TextShimmerView_android_gravity, Gravity.START)
        alignment = getInt(R.styleable.TextShimmerView_android_textAlignment, TEXT_ALIGNMENT_VIEW_START)
        maxLineCount = getInt(R.styleable.TextShimmerView_android_maxLines, 0)
        font = ResourcesCompat.getFont(context, fontId)
        shimmerColor = getResourceId(R.styleable.TextShimmerView_textShimmerColor, 0)
        textColor = getResourceId(R.styleable.TextShimmerView_android_textColor, 0)
        text = getString(R.styleable.TextShimmerView_android_text).toString()
        textSize = getDimensionPixelSize(R.styleable.TextShimmerView_android_textSize, 0).toFloat()
    }

    private fun initShimmerText() {
        shimmerTextView = findViewById(R.id.shimmer_text)

        shimmerTextView.text = text
        shimmerTextView.gravity = gravity
        shimmerTextView.textAlignment = alignment
        shimmerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        with(shimmerTextView) {
            if (maxLineCount != 0) {
                maxLines = maxLineCount
                ellipsize = TextUtils.TruncateAt.END
            }
            setTextColor(context.getColor(shimmerColor))
            typeface = font
            val radius: Float = textSize / 3
            val filter = BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL)
            paint.setMaskFilter(filter)
        }

    }

    private fun initMainText() {
        mainTextView = findViewById(R.id.main_text)
        mainTextView.gravity = gravity
        mainTextView.text = text
        mainTextView.textAlignment = alignment
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        with(mainTextView) {
            typeface = font
            if (maxLineCount != 0) {
                ellipsize = TextUtils.TruncateAt.END
                maxLines = maxLineCount
            }
            setTextColor(context.getColor(textColor))

        }
    }
}