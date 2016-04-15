package com.example.santiago.kotlintests.queue_list.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

/**
 * Created by santiago on 14/04/16.
 */
class BlurBuilder(val context: Context) {

    private val BITMAP_BLUR_RADIUS = 12F

    fun blur(image: Bitmap): Bitmap {
        val inputBitmap = Bitmap.createScaledBitmap(image, image.width, image.height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val renderScript = RenderScript.create(context)
        val intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        val tmpOutput = Allocation.createFromBitmap(renderScript, outputBitmap)

        intrinsicBlur.apply {
            setRadius(BITMAP_BLUR_RADIUS)
            setInput(Allocation.createFromBitmap(renderScript, inputBitmap))
            forEach(tmpOutput)
        }

        tmpOutput.copyTo(outputBitmap)

        image.recycle()
        inputBitmap.recycle()

        return outputBitmap
    }

}