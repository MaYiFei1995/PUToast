package com.mai.app

import android.app.Activity
import android.app.Presentation
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import com.mai.app.databinding.PresentationTestBinding
import com.mai.putoast.PUToast


class TestPresentation(private val outerContext: Context, display: Display) :
    Presentation(outerContext, display) {

    private lateinit var viewBinding: PresentationTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = PresentationTestBinding.inflate((outerContext as Activity).layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btn1.setOnClickListener {
            // 普通文字
            PUToast(this@TestPresentation, "Hello World!").show()
        }

        viewBinding.btn2.setOnClickListener {
            val puToast = PUToast(this@TestPresentation, "Hello World!")
            // 修改TextView样式
            val textView = puToast.getTextView()!!
            textView.setTextColor(outerContext.resources.getColor(R.color.purple_200))
            textView.textSize = textView.textSize + 3
            textView.gravity = Gravity.START or Gravity.CENTER_VERTICAL

            puToast.showLong()
        }

        viewBinding.btn3.setOnClickListener {
            // 自定义View显示
            val bitmap =
                BitmapFactory.decodeResource(outerContext.resources, R.mipmap.ic_launcher)
            val image = ImageView(outerContext)
            image.setImageBitmap(bitmap)
            image.scaleType = ImageView.ScaleType.CENTER_CROP
            image.layoutParams = FrameLayout.LayoutParams(
                bitmap.width, bitmap.height
            )
            PUToast(this@TestPresentation, image).show()
        }
    }

}