package myapplication.android.pixelpal.ui.custom_view

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.bumptech.glide.Glide
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

    fun setImageUri(uri: Uri){
        Glide.with(context)
            .load(uri)
            .override(LayoutParams.WRAP_CONTENT)
            .into(this)
    }

    fun setDrawableImage(image: Int){
        setImageDrawable(ResourcesCompat.getDrawable(resources, image, context.theme))
    }
}