package com.example.jkgi.natificotionfragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notification.*


class CreateNotificationFragment : Fragment() {

    private var listener: OnNotificationFragmentListener? = null
    private var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            number = it.getInt(PAGE_NUMBER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        plusButton.setOnClickListener { listener?.onPlusClick() }
        minusButton.setOnClickListener { listener?.onMinusClick(this, number) }
        createNotification.setOnClickListener {
            listener?.onCreateNotificationClick(number)
        }
        numberTextView.text = number.toString()
        if (number == 1) {
            minusButton.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNotificationFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNotificationFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnNotificationFragmentListener {
        fun onPlusClick()
        fun onMinusClick(fragment: Fragment, currentPage: Int)
        fun onCreateNotificationClick(currentPage: Int)
    }

    companion object {
        private const val PAGE_NUMBER = "pageNumber"
        @JvmStatic
        fun newInstance(pageNumber: Int) =
                CreateNotificationFragment().apply {
                    arguments = Bundle().apply {
                        putInt(PAGE_NUMBER, pageNumber)
                    }
                }
    }
}
