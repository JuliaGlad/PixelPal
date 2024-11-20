package myapplication.android.pixelpal.ui.custom_view.flexBox

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.ui.listener.ClickIntegerListener

class CreatorView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : View(context, attributeSet, defStyle, defTheme) {
    var viewId: Int = 0
        set(value) {
            if (value != field) {
                field = value
            }
        }

    var isChosen: Boolean = false
        set(value) {
            if (value != field) {
                field = value
                val id = if (value) R.drawable.creator_bg_selected
                else R.drawable.creator_bg_unselected
                background = ResourcesCompat.getDrawable(resources, id, context.theme)
                invalidate()
            }
        }

    var textMainSize: Float = 14f
        set(value) {
            if (value != field) {
                field = value
                requestLayout()
            }
        }

    var textColor: Int = context.getColor(R.color.main_blue)
        set(value) {
            if (value != field) {
                field = value
                requestLayout()
            }
        }

    var textShimmerColor: Int = context.getColor(R.color.light_blue)
        set(value) {
            if (value != field) {
                field = value
                requestLayout()
            }
        }

    var creator: String = "Empty"
        set(value) {
            if (value != field) {
                field = value
                requestLayout()
            }
        }
    var clickListener: ClickIntegerListener? = null
    private var typeFace: Typeface? = null

    fun setMainTextSize(size: Float) {
        textMainSize = size
        requestLayout()
    }

    init {
        context.withStyledAttributes(attributeSet, R.styleable.CreatorView) {
            creator = getString(R.styleable.CreatorView_creator).toString()
            textMainSize =
                getDimensionPixelSize(R.styleable.CreatorView_android_textSize, 1).toFloat()
            textColor = getColor(R.styleable.CreatorView_android_textColor, 0)
            textShimmerColor = getColor(R.styleable.CreatorView_creatorShimmerColor, 0)
            typeFace = ResourcesCompat.getFont(context, R.font.tektur_medium)
            isClickable = true
        }
    }

    private val textRect = Rect()
    private val shimmerRect = Rect()

    private val textMainPaint = Paint().apply {
        color = textColor
        typeface = typeFace
        textSize = textMainSize.toSp()
    }

    private val textShimmerPaint = Paint().apply {
        color = textShimmerColor
        typeface = typeFace
        textSize = textMainSize.toSp()
        val radius: Float = textMainSize.toSp() / 3
        maskFilter = BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        textShimmerPaint.getTextBounds(creator, 0, creator.length, shimmerRect)
        textMainPaint.getTextBounds(creator, 0, creator.length, textRect)

        val actualWidth = resolveSize(
            paddingLeft + paddingRight + textRect.width(),
            widthMeasureSpec
        )

        val actualHeight = resolveSize(
            paddingTop + paddingBottom + textRect.height(),
            heightMeasureSpec
        )

        setMeasuredDimension(actualWidth, actualHeight)
    }

    fun CreatorView.onViewClicked() {
        setOnClickListener {
            isChosen = !isChosen
            clickListener?.onClick(viewId)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textMainPaint.color = textColor
        textShimmerPaint.color = textColor
        val topOffset = height / 2 - textRect.exactCenterY()

        canvas.drawText(creator, paddingLeft.toFloat(), topOffset, textMainPaint)
        canvas.drawText(creator, paddingLeft.toFloat(), topOffset, textShimmerPaint)
    }

    private fun Float.toSp() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX, this, context.resources.displayMetrics
    )
}