package com.example.customcircleview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
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


    private val size: Int = 15
    private var listCircle = ArrayList<Circle>()
    private var listColor = ArrayList<Int>()

    init {
        setListColor()
        for (item in 0..size) {
            listCircle.add(
                Circle(
                    (0..1000).random(),
                    (0..1000).random(),
                    (10..20).random(),
                    (10..20).random(),
                    (20..50).random(),
                    listColor.random()
                )
            )
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val paint = Paint()

        for (circle in listCircle) {

            val rightLimit: Int = width - circle.radius
            val bottomLimit: Int = height - circle.radius

            circle.x += circle.speedX
            circle.y += circle.speedY
            paint.color = circle.color

            //right limit
            if (circle.x >= rightLimit) {
                circle.x = rightLimit
                circle.speedX *= -1
            }

            //left limit
            if (circle.x <= circle.radius) {
                circle.x = circle.radius
                circle.speedX *= -1
            }
            //bottom limit
            if (circle.y >= bottomLimit) {
                circle.y = bottomLimit
                circle.speedY *= -1
            }
            //top limit
            if (circle.y <= circle.radius) {
                circle.y = circle.radius
                circle.speedY *= -1
            }

            for (item in listCircle) {

                if (item != circle) {

                    val diffX = (circle.x - item.x) * (circle.x - item.x)
                    val diffY = (circle.y - item.y) * (circle.y - item.y)
                    val radius = (circle.radius + item.radius) * (circle.radius + item.radius)

                    if (diffX + diffY <= radius) {

                        circle.speedX *= -1
                        circle.speedY *= -1

                        item.speedX *= -1
                        item.speedY *= -1

                        circle.apply {
                            if (x < item.x) {
                                x -= 10
                                item.x += 10
                            } else {
                                x += 10
                                item.x -= 10
                            }

                            if (y < item.y) {
                                y -= 10
                                item.y += 10
                            } else {
                                y += 10
                                item.y -= 10
                            }
                        }
                    }
                }
            }
            canvas?.drawCircle(
                circle.x.toFloat(),
                circle.y.toFloat(),
                circle.radius.toFloat(),
                paint
            )
        }

        invalidate()

    }

    private fun setListColor() {
        listColor.add(Color.RED)
        listColor.add(Color.GREEN)
        listColor.add(Color.YELLOW)
        listColor.add(Color.BLUE)
    }
}