package com.hello780831.automatedprocess.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class MyAccessibilityService : AccessibilityService() {

    private val TAG = "AccessibilityService"

    override fun onInterrupt() {
        Log.d(TAG,"onInterrupt")
    }

    //org.telegram.ui.LaunchActivity

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val eventType = event!!.eventType
        val packageName = event.packageName
        val className = event.className
        when(eventType){
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ->{
                Log.d(TAG,"TYPE_WINDOW_STATE_CHANGED\nclassName :$className")
            }
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED ->{
                Log.d(TAG,"TYPE_NOTIFICATION_STATE_CHANGED")
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED ->{
                Log.d(TAG,"TYPE_VIEW_CLICKED")
            }
            AccessibilityEvent.TYPE_VIEW_SCROLLED ->{
                Log.d(TAG,"TYPE_VIEW_SCROLLED\nclassName :$className")
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED ->{
                Log.d(TAG,"TYPE_VIEW_FOCUSED\nclassName :$className")
            }
        }
//        Log.d(TAG,"event :\n" +
//                "type :${event!!.eventType}\n" +
//                "packageName :${event.packageName}")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG,"onServiceConnected")
    }


}