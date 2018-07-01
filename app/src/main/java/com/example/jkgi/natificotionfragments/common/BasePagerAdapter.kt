package com.example.jkgi.natificotionfragments.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import java.util.*




class BasePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val list = ArrayList<Fragment>()

    fun add (fragment: Fragment): BasePagerAdapter {
        list.add(fragment)
        notifyDataSetChanged()
        return this
    }

    fun remove(fragment: Fragment){
        list.remove(fragment)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int) = list[position]


    override fun getItemPosition(`object`: Any): Int {
        val index = list.indexOf(`object`)

        return if (index == -1)
            PagerAdapter.POSITION_NONE
        else
            index
    }


}
