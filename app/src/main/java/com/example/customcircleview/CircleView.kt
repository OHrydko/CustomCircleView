package com.example.customcircleview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*


class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyle, defStyleRes) {


    private val radius = 46
    private val size: Int = 15
    private var listCircle = ArrayList<Circle>()

    init {
        for (item in 0..size) {
            listCircle.add(Circle((0..1000).random(), (0..1000).random()))
        }
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val paint = Paint()

        val rightLimit: Int = width - radius
        val bottomLimit: Int = height - radius

        for (circle in listCircle) {
            circle.x += circle.speedX
            circle.y += circle.speedY
            paint.color = circle.color

            //right limit
            if (circle.x >= rightLimit) {
                circle.x = rightLimit
                circle.speedX *= -1
            }

            //left limit
            if (circle.x <= radius) {
                circle.x = radius
                circle.speedX *= -1
            }
            //bottom limit
            if (circle.y >= bottomLimit) {
                circle.y = bottomLimit
                circle.speedY *= -1
            }
            //top limit
            if (circle.y <= radius) {
                circle.y = radius
                circle.speedY *= -1
            }

            for (item in listCircle) {

                if (item != circle) {

                    val diffSqrtX = (circle.x - item.x) * (circle.x - item.x)
                    val diffSqrtY = (circle.y - item.y) * (circle.y - item.y)
                    val radiusSqrt = (radius + radius) * (radius + radius)

                    if (diffSqrtX + diffSqrtY <= radiusSqrt) {

                        circle.speedX *= -1
                        circle.speedY *= -1

                        item.speedX *= -1
                        item.speedY *= -1
                        circle.apply {
                            if (x < item.x) {
                                x -= 1
                                item.x += 1
                            } else {
                                x += 1
                                item.x -= 1
                            }

                            if (y < item.y) {
                                y -= 1
                                item.y += 1
                            } else {
                                y += 1
                                item.y -= 1
                            }
                        }
                    }
                }
            }
            canvas?.drawCircle(circle.x.toFloat(), circle.y.toFloat(), radius.toFloat(), paint)
        }

        invalidate()

    }
}