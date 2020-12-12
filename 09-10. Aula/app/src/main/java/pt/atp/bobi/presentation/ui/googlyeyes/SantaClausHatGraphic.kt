package pt.atp.bobi.presentation.ui.googlyeyes

import android.graphics.*
import pt.atp.bobi.R
import pt.atp.bobi.presentation.ui.googlyeyes.ui.camera.GraphicOverlay

class SantaClausHatGraphic(val overlay: GraphicOverlay): GraphicOverlay.Graphic(overlay) {

    private val paint = Paint()

    private var position: PointF? = null

    private val santaClausHat: Bitmap by lazy {
        val bitmap = BitmapFactory.decodeResource(overlay.context.resources, R.drawable.santa_claus)
        Bitmap.createScaledBitmap(bitmap, 800, 700, true)
    }

    override fun draw(canvas: Canvas) {
        if (position == null) {
            return
        }

        val x = translateX(position!!.x) - santaClausHat.width / 1.65f
        val y = translateY(position!!.y) - santaClausHat.height * 1.10f

        canvas.drawBitmap(santaClausHat, x, y, paint)
    }

    fun updatePosition(newPosition: PointF) {
        position = newPosition
        postInvalidate()
    }
}