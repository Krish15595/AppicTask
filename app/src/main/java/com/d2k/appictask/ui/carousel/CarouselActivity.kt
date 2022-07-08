package com.d2k.appictask.ui.carousel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.ViewPager
import com.d2k.appictask.ui.main.MainActivity
import com.d2k.duediligence.ui.carousel.SectionsPagerAdapter
import com.d2k.appictask.R
import com.d2k.appictask.ui.login.LoginActivity
import com.d2k.appictask.util.Constants.Companion.IS_FIRST_LAUNCH
import com.d2k.tmb.base.BaseActivity
import com.d2k.tmb.extension.launchActivity
import kotlinx.android.synthetic.main.activity_carousel.*

class CarouselActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carousel)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = findViewById(R.id.view_pager)
        view_pager.adapter = sectionsPagerAdapter
        dots.setViewPager(view_pager)

        val dots: ArrayList<View> = arrayListOf<View>(dot1, dot2, dot3)
//        dots[0].background = ContextCompat.getDrawable(applicationContext, R.drawable.dot_unselected)
//        dots[1].background = ContextCompat.getDrawable(applicationContext, R.drawable.dot_unselected)
//        dots[2].background = ContextCompat.getDrawable(applicationContext, R.drawable.dot_unselected)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                for ((i, _) in dots.withIndex())
                    dots[position].background = ContextCompat.getDrawable(applicationContext,
                            if (i == position) R.drawable.dot_selected else R.drawable.dot_unselected)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                 if (position == 1)
                     tvNext.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_getting_started,null))
                 else
                     tvNext.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_right_arrow,null))
            }
        })

        tvNext.setOnClickListener {
            if (view_pager.currentItem == 1) {
                startingActivity()
            } else {
                view_pager.currentItem = view_pager.currentItem + 1
            }
        }
        tvSkip.setOnClickListener {
            startingActivity()
        }
    }

    //
//    @Inject
//    lateinit var preference: MyPreference;
    public fun startingActivity() {
        prefUtils.setBoolean(IS_FIRST_LAUNCH, true)
        launchActivity<LoginActivity> {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            finish()
        }
    }
}
