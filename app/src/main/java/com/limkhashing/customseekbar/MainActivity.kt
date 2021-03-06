package com.limkhashing.customseekbar

import android.graphics.Typeface
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val textView = TextView(this@MainActivity)
                textView.text = "RECOMMENDED"
                textView.id = View.generateViewId()
                textView.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_rectangle), null, null)
                textView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.mid_green))
                textView.setTypeface(null, Typeface.BOLD)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10F)

                seekbar .afterLayout {
                    // Start Padding = seekbarDefaultPadding + (seekbar Width - seekbarDefaultPadding * 2) * (recommended tick  / how many bar)) - (TextView Width / 2)
                    textView.measure(0, 0)
                    val seekbarDefaultPadding = dpToPixels(resources.displayMetrics, 16F)
                    val paddingStart = seekbarDefaultPadding + ((seekbar.width - seekbarDefaultPadding * 2 )  * (5 / 10F)) - (textView.measuredWidth / 2F)

                    val set = ConstraintSet()
                    layout.addView(textView)
                    set.clone(layout)
                    set.connect(textView.id, ConstraintSet.TOP, seekbar.id, ConstraintSet.BOTTOM, 16)
                    set.connect(textView.id, ConstraintSet.START, seekbar.id, ConstraintSet.START, paddingStart.toInt())
                    set.applyTo(layout)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        seekbar.progress = 8
    }

    fun dpToPixels(display_metrics: DisplayMetrics, dps: Float): Int {
        val scale = display_metrics.density
        return (dps * scale + 0.5f).toInt()
    }
}

fun View.afterLayout(what: () -> Unit) {
    if(isLaidOut) {
        what.invoke()
    } else {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                what.invoke()
            }
        })
    }
}
