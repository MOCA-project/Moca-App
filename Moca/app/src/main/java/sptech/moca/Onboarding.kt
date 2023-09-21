package sptech.moca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager

class Onboarding : AppCompatActivity() {

    private lateinit var dots: Array<TextView>
    private lateinit var viewPager: ViewPager
    private lateinit var linearLayout: LinearLayout
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var btnComecar: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.slider)
        linearLayout = findViewById(R.id.linear_layout_onboarding)
        btnComecar = findViewById(R.id.btn_comecar)

        sliderAdapter = SliderAdapter(this)

        viewPager.adapter = sliderAdapter

        addLinearLayoutOnboarding(0)
        viewPager.addOnPageChangeListener(changeListener)








//==================================================================================================
//        Mudar para a tela de Dashboard
//==================================================================================================
        btnComecar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }




    private fun addLinearLayoutOnboarding(position: Int) {
        dots = Array(sliderAdapter.count) { TextView(this) }
        linearLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i].text = HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots[i].textSize = 35f // 35f para indicar que é um valor float

            linearLayout.addView(dots[i])
        }

        if (dots.size > 0) {
            dots[position].setTextColor(ContextCompat.getColor(this, R.color.verde))
        }
    }

    private val changeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // Implemente o comportamento desejado aqui
        }

        override fun onPageSelected(position: Int) {
            // Implemente o comportamento desejado aqui
            addLinearLayoutOnboarding(position)

            if (position == 0) {
                btnComecar.visibility = View.INVISIBLE
            } else if (position == 1) {
                btnComecar.visibility = View.INVISIBLE
            } else if (position == 2) {
                btnComecar.visibility = View.INVISIBLE
            } else {
                btnComecar.visibility = View.VISIBLE
            }

        }

        override fun onPageScrollStateChanged(state: Int) {
            // Implemente o comportamento desejado aqui
        }
    }
}
