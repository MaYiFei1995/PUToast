package com.mai.putoast

import android.app.Presentation
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

/**
 * 使用PopupWindow模拟Toast，用于显示在Presentation界面之上
 */
class PUToast : PopupWindow {

    private val dialog: Presentation
    private var text: String? = null
    private var view: View? = null

    private val mHandler: Handler by lazy {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                this@PUToast.dismiss()
            }
        }
    }

    /**
     * 显示文字
     */
    constructor(dialog: Presentation, text: String) {
        this.dialog = dialog
        this.text = text

        init()
    }

    /**
     * 显示自定义view
     */
    constructor(dialog: Presentation, view: View) {
        this.dialog = dialog
        this.view = view

        init()
    }

    private fun init() {
        if (this.view == null)
            this.view = generateContentView()
        this.view!!.alpha = 0f
        this.contentView = this.view
        super.setOutsideTouchable(false)

        // 配置windowLayoutType
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            this.windowLayoutType = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.windowLayoutType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
    }

    /**
     * mOutsideTouchable不可修改
     */
    override fun setOutsideTouchable(touchable: Boolean) {

    }

    /**
     * 生成默认的TextView
     */
    private fun generateContentView(): View {
        val view = AppCompatTextView(dialog.context)
        view.setBackgroundResource(R.drawable.popup_toast_bg)
        view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        view.gravity = Gravity.CENTER
        view.text = this.text
        view.setTextColor(Color.WHITE)

        return view
    }

    /**
     * 获取当前的TextView，用于自定义TextView样式
     * @return 当前contentView为TextView时返回对象，否则返回null
     */
    fun getTextView(): TextView? {
        return if (this.view != null && this.view is TextView)
            this.view as TextView
        else null
    }

    fun show() {
        show(1000)
    }

    fun showLong() {
        show(2000)
    }

    /**
     * 展示
     */
    private fun show(autoDismissMills: Long) {
        if (view == null) {
            Log.e("PUToast", "Content view is null")
            return
        }
        val width: Int
        val height: Int
        if (view is TextView) {
            width = (view as TextView).paint.measureText(text).toInt() + 30            //默认一行
            height = (view as TextView).paint.measureText("AB").toInt() + 15     //默认一行
        } else {
            width = this.view!!.layoutParams.width
            height = this.view!!.layoutParams.height
        }

        this.width = width
        this.height = height

        val anchor = dialog.window!!.decorView      //获取View
        super.showAtLocation(
            anchor,
            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
            0,
            anchor.height / 6
        )
        showAnimator()
        mHandler.sendEmptyMessageDelayed(1, autoDismissMills + 200)
    }

    /**
     * 隐藏
     */
    override fun dismiss() {
        try {
            mHandler.removeCallbacksAndMessages(null)
        } catch (ignore: Exception) {
        }
        dismissAnimator()
    }

    /**
     * 显示动画
     */
    private fun showAnimator() {
        contentView.animate()
            .alpha(1f)
            .setDuration(200)
            .start()
    }

    /**
     * 隐藏动画
     */
    private fun dismissAnimator() {
        contentView.animate()
            .alpha(0f)
            .setDuration(200)
            .withEndAction { super.dismiss() }
            .start()
    }

}