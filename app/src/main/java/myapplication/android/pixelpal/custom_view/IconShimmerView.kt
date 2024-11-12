package myapplication.android.pixelpal.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.withStyledAttributes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import myapplication.android.pixelpal.R

class IconShimmerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var mainImage: ImageView
    private lateinit var shimmerImage: ImageView
    var shimmerColor: Int = 0
        set(value) {
            field = value
            requestLayout()
        }

    var iconColor: Int = 0
        set(value) {
            field = value
            requestLayout()
        }

    var icon: Drawable? = null
        set(value) {
            field = value
            requestLayout()
        }

    val shimmerIcon get() = icon!!

    private val blur = BlurTransformation(10, 1)

    init {
        LayoutInflater.from(context).inflate(R.layout.icon_shimmer, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.IconShimmerView) {
            initVariables()
            initMainImage()
            initShimmerImage()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        measureChildWithMargins(mainImage, widthMeasureSpec, 0, heightMeasureSpec, 0)
        measureChildWithMargins(shimmerImage, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val actualWidth = mainImage.measuredWidth + paddingLeft + paddingRight
        val actualHeight = mainImage.measuredHeight + paddingTop + paddingBottom

        setMeasuredDimension(
            resolveSize(actualWidth, widthMeasureSpec),
            resolveSize(actualHeight, heightMeasureSpec)
        )
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        onImageLayout(shimmerImage)
        onImageLayout(mainImage)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    private fun initShimmerImage() {
        shimmerImage = findViewById(R.id.shimmer_icon)
        shimmerIcon.setTint(shimmerColor)
        shimmerImage.setImageDrawable(shimmerIcon)

        Glide.with(this)
            .load(shimmerIcon)
            .apply(RequestOptions.bitmapTransform(blur))
            .into(shimmerImage)
    }

    private fun initMainImage() {
        mainImage = findViewById(R.id.main_icon)
        icon?.setTint(iconColor)
        mainImage.setImageDrawable(icon)
    }

    private fun TypedArray.initVariables() {
        shimmerColor = getColor(R.styleable.IconShimmerView_shimmerColor, 0)
        iconColor = getColor(R.styleable.IconShimmerView_iconColor, 0)
        icon = getDrawable(R.styleable.IconShimmerView_android_drawable)
    }

    private fun onImageLayout(imageView: ImageView) {
        imageView.layout(
            paddingLeft,
            paddingTop,
            paddingLeft + imageView.measuredWidth,
            paddingTop + imageView.measuredHeight
        )
    }
}