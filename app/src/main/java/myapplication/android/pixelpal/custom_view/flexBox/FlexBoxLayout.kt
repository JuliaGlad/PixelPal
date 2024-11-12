package myapplication.android.pixelpal.custom_view.flexBox

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import myapplication.android.flexboxlayout.LayoutAlignment
import myapplication.android.pixelpal.R

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {
    private val viewIndexes = mutableListOf<Int>()
    var alignment = LayoutAlignment.LEFT
        set(value) {
            field = value
            if (field != value){
                requestLayout()
            }
        }

    init {
        context.withStyledAttributes(attributeSet, R.styleable.FlexBoxLayout) {
            alignment = getInt(R.styleable.FlexBoxLayout_layout_alignment, LayoutAlignment.LEFT)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var currentWidth = 0
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        var actualWidth = 0
        var actualHeight = 0
        var maxLineHeight = 0
        var severalLines = false
        var lineWidth = 0

        children.forEach {
            measureChild(it, widthMeasureSpec, heightMeasureSpec)
            val viewWidth = it.measuredWidth + it.marginLeft + it.marginRight + INTERVAL
            val viewHeight = it.measuredHeight + it.marginBottom + it.marginTop

            currentWidth += viewWidth

            if (maxLineHeight < viewHeight && currentWidth < parentWidth) {
                maxLineHeight = viewHeight
            }

            if (currentWidth + paddingLeft + paddingRight > parentWidth) {
                if (!severalLines) {
                    severalLines = true
                }
                actualHeight += INTERVAL
                viewIndexes.add(children.indexOf(it))
                if (lineWidth > actualWidth) {
                    actualWidth = lineWidth
                }
                actualHeight += maxLineHeight
                maxLineHeight = viewHeight
                currentWidth = viewWidth
                lineWidth = viewWidth

            } else {
                lineWidth += viewWidth
                if (lineWidth > actualWidth) {
                    actualWidth = lineWidth
                }
            }

            if (it.isLast()) {
                actualHeight += maxLineHeight
            }
        }

        if (actualHeight <= 0) {
            actualHeight = maxLineHeight
        }

        actualHeight += paddingTop + paddingBottom
        actualWidth += paddingLeft + paddingRight

        setMeasuredDimension(
            resolveSize(actualWidth, widthMeasureSpec),
            resolveSize(actualHeight, heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val parentStartPadding = paddingLeft
        var actualHeight = paddingTop
        var previousWidth = parentStartPadding
        var childrenPassed = 0
        var currentLine = 0
        var maxLineHeight = 0

        children.forEach {
            with(it) {
                val viewHeight = it.marginTop + it.measuredHeight + it.marginBottom

                if (!viewIndexes.contains(indexOfChild(it)) || childrenPassed == 0) {
                    if (maxLineHeight == 0 || maxLineHeight < viewHeight) {
                        maxLineHeight = viewHeight
                    }
                } else {
                    currentLine++
                    previousWidth = parentStartPadding
                    actualHeight += maxLineHeight + INTERVAL
                    maxLineHeight = viewHeight
                }

                val (top, bottom) = getVerticalPositions(it, actualHeight)
                val (left, right) = getHorizontalPositions(it, r, previousWidth)

                layout(left, top, right, bottom)

                childrenPassed++
                previousWidth += (measuredWidth + marginLeft + marginRight + INTERVAL)
            }
        }
    }

    private fun getVerticalPositions(view: View, actualHeight: Int): Pair<Int, Int> {
        val top = view.marginTop + actualHeight
        val bottom = view.measuredHeight + top
        return Pair(top, bottom)
    }

    private fun getHorizontalPositions(view: View, r: Int, previousWidth: Int = 0): Pair<Int, Int> {
        var left =
            if (alignment == LayoutAlignment.RIGHT) r - view.measuredWidth - previousWidth - view.marginLeft - INTERVAL
            else previousWidth + view.marginLeft

        if (previousWidth != 0) left += INTERVAL

        val right =
            if (alignment == LayoutAlignment.RIGHT) r - previousWidth - INTERVAL
            else view.measuredWidth + previousWidth + INTERVAL


        return Pair(left, right)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    private fun View.isLast() = children.indexOf(this) == childCount - 1

    companion object {
        const val INTERVAL = 20
    }
}