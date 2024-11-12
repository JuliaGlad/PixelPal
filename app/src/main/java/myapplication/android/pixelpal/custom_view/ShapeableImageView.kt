package myapplication.android.pixelpal.custom_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import myapplication.android.pixelpal.R

class ShapeableImageView @JvmOverloads constructor(
    context: Context,
    defAttrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, defAttrs, defStyle){

    private var backgroundForm: Drawable? = null
        set(value) {
            field = value
            setBackgroundDrawable(value)
            requestLayout()
        }

    init {
        clipToOutline = true
        context.withStyledAttributes(defAttrs, R.styleable.ShapeableImageView){
            backgroundForm = getDrawable(R.styleable.ShapeableImageView_android_background)
            setBackgroundDrawable(backgroundForm)
        }
        scaleType = ScaleType.CENTER_CROP
    }

    fun setDrawableImage(image: Int){
        setImageDrawable(ResourcesCompat.getDrawable(resources, image, context.theme))
    }
}