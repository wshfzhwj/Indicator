package com.aniu.indicator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.aniu.indicatorlib.LineIndicator

class MainActivity : AppCompatActivity() {

    private var mViewPager: ViewPager? = null
    private lateinit var mLinIndicator:LineIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewPager = findViewById(R.id.viewpager)
        mLinIndicator = findViewById(R.id.indicator)

        var list = ArrayList<String>()
        list.add("No.1")
        list.add("No.2")
        list.add("No.3")
        list.add("No.4")

        var fragments = ArrayList<Fragment>()
        for (i in list) {
            val fragment = BlankFragment()
            val bundle = Bundle()
            bundle.putString("key", i)
            fragment.arguments = bundle
            fragments.add(fragment)
        }

        val allNum = 100000
        val num = list.size
        var firstIndex = 0
        if (num > 0) {
            allNum / num / 2 * num
        }

        val adapter = ViewPagerAdapter(supportFragmentManager, list, num, allNum)
        mViewPager!!.adapter = adapter
        mViewPager!!.currentItem = firstIndex

        mLinIndicator.setViewPager(mViewPager,num,false)
    }

    internal inner class ViewPagerAdapter(fm: FragmentManager, strings: ArrayList<String>, num: Int, count: Int) :
        FragmentStatePagerAdapter(fm) {

        private val mStrings: ArrayList<String> = strings
        private val mNum: Int = num
        private val mAllNum: Int = count

        override fun getItem(position: Int): Fragment {
            val blankFragment = BlankFragment()
            val bundle = Bundle()
            if (mNum > 0) {
                bundle.putString("key", mStrings[position % mNum])
            }
            blankFragment.arguments = bundle
            return blankFragment
        }

        override fun getCount(): Int {
            return mAllNum
        }

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }

    }
}
