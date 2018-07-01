package com.example.jkgi.natificotionfragments

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.jkgi.natificotionfragments.common.BasePagerAdapter
import com.example.jkgi.natificotionfragments.util.NotificationUtil
import com.example.jkgi.natificotionfragments.util.PreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CreateNotificationFragment.OnNotificationFragmentListener {

    companion object {
        private const val PAGES_KEY: String = "pagesKey"
        private const val DISPLAY_PAGE_NUMBER = "displayPageNumber"

        @JvmStatic
        fun newInstance(context: Context, pageNumber: Int): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(DISPLAY_PAGE_NUMBER, pageNumber)
            return intent
        }
    }

    private lateinit var adapter: BasePagerAdapter

    private lateinit var notificationManager: NotificationManager
    private var lastPageNumber: Int = 0
    private var pageList: ArrayList<Int> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        PreferenceUtil.getArrayList(PAGES_KEY, this)?.let { pageList = it }
        initView()
    }

    private fun initView() {
        adapter = BasePagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        if (pageList.size > 0) {
            pageList.forEach { addFragment(it) }
            lastPageNumber = pageList.last()
            intent.getIntExtra(DISPLAY_PAGE_NUMBER, 1).let { page ->
                viewPager.currentItem = pageList.indexOfFirst { it == page }
            }
        } else {
            onPlusClick()
        }
    }

    private fun addFragment(page: Int) {
        adapter.add(CreateNotificationFragment.newInstance(page))
        if (adapter.count > 0) viewPager.setCurrentItem(
                adapter.count - 1,
                true
        )
    }

    override fun onPlusClick() {
        lastPageNumber++
        addFragment(lastPageNumber)
        pageList.add(lastPageNumber)
        saveListPages()
    }

    override fun onMinusClick(fragment: Fragment, currentPage: Int) {
        adapter.remove(fragment)
        notificationManager.cancel(currentPage)
        pageList.remove(currentPage)
        saveListPages()
    }

    override fun onCreateNotificationClick(currentPage: Int) {
        NotificationUtil.createNotification(this, notificationManager, currentPage)
    }

    private fun saveListPages(){
        PreferenceUtil.saveArrayList(pageList, PAGES_KEY, this)
    }
}
