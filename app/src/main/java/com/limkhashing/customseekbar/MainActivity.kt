package com.limkhashing.customseekbar

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs


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

                seekbar.measure(0 , 0)
//                Log.d("MainActivity", "Seekbar width = " + seekbar.width)

                textView.measure(0, 0)
//                Log.d("MainActivity", "Textview width = " + textView.measuredWidth.toString())
//                Log.d("MainActivity", "String width = " + Paint().measureText(textView.text.toString()).toString())

                val paddingStart = (1440 * (3/10F)) - (textView.measuredWidth / 2F)
                Log.d("MainActivity", (3/10F).toString())
                Log.d("MainActivity", (1440 * (3/10F)).toString())
                Log.d("MainActivity", (textView.measuredWidth / 2F).toString())
//                Log.d("MainActivity", "Total padding start = " + paddingStart.toString())

                val set = ConstraintSet()
                layout.addView(textView)
                set.clone(layout)
                set.connect(textView.id, ConstraintSet.TOP, seekbar.id, ConstraintSet.BOTTOM, 16)
                set.connect(textView.id, ConstraintSet.START, seekbar.id, ConstraintSet.START, paddingStart.toInt())
                set.applyTo(layout)
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
