package com.tak8997.instastylegallery.transitions

import android.animation.*
import android.graphics.*
import android.graphics.drawable.Drawable
import android.transition.Transition
import android.transition.TransitionValues
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

internal class TextResizeTransition : Transition() {

    companion object {
        private const val FONT_SIZE = "TextResize:fontSize"
        private const val DATA = "TextResize:data"

        private val PROPERTIES = arrayOf(
            FONT_SIZE
        )
    }

    init {
        addTarget(TextView::class.java)
    }

    override fun captureStartValues(transitionValues: TransitionValues?) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues?) {
        captureValues(transitionValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        if (startValues == null || endValues == null) {
            return ObjectAnimator()
        }

        val startData = startValues.values[DATA] as TextResizeData
        val endData = endValues.values[DATA] as TextResizeData
        if (startData.gravity != endData.gravity) {
            return ObjectAnimator()
        }

        val textView = endValues.view as TextView
        var startFontSize = startValues.values[FONT_SIZE] as Float
        // Capture the start bitmap -- we need to set the values to the start values first
        setTextViewData(textView, startData, startFontSize)
        val startWidth = textView.paint.measureText(textView.text.toString())

        val startBitmap = captureTextBitmap(textView)

        if (startBitmap == null) {
            startFontSize = 0f
        }

        var endFontSize = endValues.values[FONT_SIZE] as Float

        // Set the values to the end values
        setTextViewData(textView, endData, endFontSize)

        val endWidth = textView.paint.measureText(textView.text.toString())

        // Capture the end bitmap
        val endBitmap = captureTextBitmap(textView)
        if (endBitmap == null) {
            endFontSize = 0f
        }

        if (startFontSize == 0f && endFontSize == 0f) {
            return ObjectAnimator() // Can't animate null bitmaps
        }

        // Set the colors of the TextView so that nothing is drawn.
        // Only draw the bitmaps in the overlay.
        val textColors = textView.textColors
        val hintColors = textView.hintTextColors
        val highlightColor = textView.highlightColor
        val linkColors = textView.linkTextColors
        textView.setTextColor(Color.TRANSPARENT)
        textView.setHintTextColor(Color.TRANSPARENT)
        textView.highlightColor = Color.TRANSPARENT
        textView.setLinkTextColor(Color.TRANSPARENT)

        // Create the drawable that will be animated in the TextView's overlay.
        // Ensure that it is showing the start state now.
        val drawable = SwitchBitmapDrawable(
            textView, startData.gravity,
            startBitmap!!, startFontSize, startWidth, endBitmap!!, endFontSize, endWidth
        )
        textView.overlay.add(drawable)

        // Properties: left, top, font size, text color
        val leftProp =
            PropertyValuesHolder.ofFloat("left", startData.paddingLeft.toFloat(), endData.paddingLeft.toFloat())
        val topProp = PropertyValuesHolder.ofFloat("top", startData.paddingTop.toFloat(), endData.paddingTop.toFloat())
        val rightProp = PropertyValuesHolder.ofFloat(
            "right",
            (startData.width - startData.paddingRight).toFloat(),
            (endData.width - endData.paddingRight).toFloat()
        )
        val bottomProp = PropertyValuesHolder.ofFloat(
            "bottom",
            (startData.height - startData.paddingBottom).toFloat(),
            (endData.height - endData.paddingBottom).toFloat()
        )
        val fontSizeProp = PropertyValuesHolder.ofFloat("fontSize", startFontSize, endFontSize)
        val animator: ObjectAnimator
        if (startData.textColor != endData.textColor) {
            val textColorProp = PropertyValuesHolder.ofObject(
                "textColor",
                ArgbEvaluator(), startData.textColor, endData.textColor
            )
            animator = ObjectAnimator.ofPropertyValuesHolder(
                drawable,
                leftProp, topProp, rightProp, bottomProp, fontSizeProp, textColorProp
            )
        } else {
            animator = ObjectAnimator.ofPropertyValuesHolder(
                drawable,
                leftProp, topProp, rightProp, bottomProp, fontSizeProp
            )
        }

        val finalFontSize = endFontSize
        val listener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                textView.overlay.remove(drawable)
                textView.setTextColor(textColors)
                textView.setHintTextColor(hintColors)
                textView.highlightColor = highlightColor
                textView.setLinkTextColor(linkColors)
            }

            override fun onAnimationPause(animation: Animator) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, drawable.fontSize)
                val paddingLeft = Math.round(drawable.left)
                val paddingTop = Math.round(drawable.top)
                val fraction = animator.animatedFraction
                val paddingRight = Math.round(
                    interpolate(
                        startData.paddingRight.toFloat(),
                        endData.paddingRight.toFloat(), fraction
                    )
                )
                val paddingBottom = Math.round(
                    interpolate(
                        startData.paddingBottom.toFloat(),
                        endData.paddingBottom.toFloat(), fraction
                    )
                )
                textView.setPadding(paddingLeft.toInt(),
                    paddingTop.toInt(), paddingRight, paddingBottom)
                textView.setTextColor(drawable.textColor)
            }

            override fun onAnimationResume(animation: Animator) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, finalFontSize)
                textView.setPadding(
                    endData.paddingLeft, endData.paddingTop,
                    endData.paddingRight, endData.paddingBottom
                )
                textView.setTextColor(endData.textColor)
            }
        }
        animator.addListener(listener)
        animator.addPauseListener(listener)
        return animator
    }

    private fun setTextViewData(view: TextView, data: TextResizeData, fontSize: Float) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
        view.setPadding(data.paddingLeft, data.paddingTop, data.paddingRight, data.paddingBottom)
        view.right = view.left + data.width
        view.bottom = view.top + data.height
        view.setTextColor(data.textColor)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(view.height, View.MeasureSpec.EXACTLY)
        view.measure(widthSpec, heightSpec)
        view.layout(view.left, view.top, view.right, view.bottom)
    }

    private fun captureTextBitmap(textView: TextView): Bitmap? {
        val background = textView.background
        textView.background = null
        val width = textView.width - textView.paddingLeft - textView.paddingRight
        val height = textView.height - textView.paddingTop - textView.paddingBottom
        if (width == 0 || height == 0) {
            return null
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.translate((-textView.paddingLeft).toFloat(), (-textView.paddingTop).toFloat())
        textView.draw(canvas)
        textView.background = background
        return bitmap
    }

    private fun interpolate(start: Float, end: Float, fraction: Float): Float {
        return start + fraction * (end - start)
    }

    private fun captureValues(transitionValues: TransitionValues?) {
        if (transitionValues?.view !is TextView) {
            return
        }

        val view = transitionValues.view as TextView
        val fontSize = view.textSize
        transitionValues.values[FONT_SIZE] = fontSize
        val data = TextResizeData(view)
        transitionValues.values[DATA] = data
    }

    internal class SwitchBitmapDrawable(
        private val view: TextView,
        gravity: Int,
        private val startBitmap: Bitmap,
        private val startFontSize: Float,
        private val startWidth: Float,
        private val endBitmap: Bitmap,
        private val endFontSize: Float,
        private val endWidth: Float
    ) : Drawable() {
        private val horizontalGravity: Int
        private val verticalGravity: Int
        private val paint = Paint()
        /**
         * @return The font size of the text in the displayed bitmap.
         */
        /**
         * Sets the font size that the text should be displayed at.
         *
         * @param fontSize The font size in pixels of the scaled bitmap text.
         */
        var fontSize: Float = 0.toFloat()
            set(fontSize) {
                field = fontSize
                invalidateSelf()
            }
        /**
         * @return The left side of the text.
         */
        /**
         * Sets the left side of the text. This should be the same as the left padding.
         *
         * @param left The left side of the text in pixels.
         */
        var left: Float = 0.toFloat()
            set(left) {
                field = left
                invalidateSelf()
            }
        /**
         * @return The top of the text.
         */
        /**
         * Sets the top of the text. This should be the same as the top padding.
         *
         * @param top The top of the text in pixels.
         */
        var top: Float = 0.toFloat()
            set(top) {
                field = top
                invalidateSelf()
            }
        /**
         * @return The right side of the text.
         */
        /**
         * Sets the right of the drawable.
         *
         * @param right The right pixel of the drawn area.
         */
        var right: Float = 0.toFloat()
            set(right) {
                field = right
                invalidateSelf()
            }
        /**
         * @return The bottom of the text.
         */
        /**
         * Sets the bottom of the drawable.
         *
         * @param bottom The bottom pixel of the drawn area.
         */
        var bottom: Float = 0.toFloat()
            set(bottom) {
                field = bottom
                invalidateSelf()
            }
        /**
         * @return The color of the text being displayed.
         */
        /**
         * Sets the color of the text to be displayed.
         *
         * @param textColor The color of the text to be displayed.
         */
        var textColor: Int = 0
            set(textColor) {
                field = textColor
                setColorFilter(textColor, PorterDuff.Mode.SRC_IN)
                invalidateSelf()
            }

        init {
            this.horizontalGravity = gravity and Gravity.HORIZONTAL_GRAVITY_MASK
            this.verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK
        }

        override fun invalidateSelf() {
            super.invalidateSelf()
            view.invalidate()
        }

        override fun draw(canvas: Canvas) {
            val saveCount = canvas.save()
            // The threshold changes depending on the target font sizes. Because scaled-up
            // fonts look bad, we want to switch when closer to the smaller font size. This
            // algorithm ensures that null bitmaps (font size = 0) are never used.
            val threshold = startFontSize / (startFontSize + endFontSize)
            val fontSize = fontSize
            val progress = (fontSize - startFontSize) / (endFontSize - startFontSize)

            // The drawn text width is a more accurate scale than font size. This avoids
            // jump when switching bitmaps.
            val expectedWidth = interpolate(startWidth, endWidth, progress)
            if (progress < threshold) {
                // draw start bitmap
                val scale = expectedWidth / startWidth
                val tx = getTranslationPoint(
                    horizontalGravity, this.left, this.right,
                    startBitmap.width.toFloat(), scale
                )
                val ty = getTranslationPoint(
                    verticalGravity, this.top, this.bottom,
                    startBitmap.height.toFloat(), scale
                )
                canvas.translate(tx, ty)
                canvas.scale(scale, scale)
                canvas.drawBitmap(startBitmap, 0f, 0f, paint)
            } else {
                // draw end bitmap
                val scale = expectedWidth / endWidth
                val tx = getTranslationPoint(
                    horizontalGravity, this.left, this.right,
                    endBitmap.width.toFloat(), scale
                )
                val ty = getTranslationPoint(
                    verticalGravity, this.top, this.bottom,
                    endBitmap.height.toFloat(), scale
                )
                canvas.translate(tx, ty)
                canvas.scale(scale, scale)
                canvas.drawBitmap(endBitmap, 0f, 0f, paint)
            }
            canvas.restoreToCount(saveCount)
        }

        override fun setAlpha(alpha: Int) {}

        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
        }

        private fun interpolate(start: Float, end: Float, fraction: Float): Float {
            return start + fraction * (end - start)
        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSLUCENT
        }

        private fun getTranslationPoint(
            gravity: Int,
            start: Float,
            end: Float,
            dim: Float,
            scale: Float
        ): Float {
            when (gravity) {
                Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL -> return (start + end - dim * scale) / 2f
                Gravity.RIGHT, Gravity.BOTTOM -> return end - dim * scale
                Gravity.LEFT, Gravity.TOP -> return start
                else -> return start
            }
        }
    }

    internal class TextResizeData(textView: TextView) {
        val paddingLeft = textView.paddingLeft
        val paddingTop = textView.paddingTop
        val paddingRight = textView.paddingRight
        val paddingBottom = textView.paddingBottom
        val width = textView.width
        val height = textView.height
        val gravity = textView.gravity
        val textColor = textView.currentTextColor
    }
}
