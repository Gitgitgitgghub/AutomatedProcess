package com.hello780831.automatedprocess


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hello780831.automatedprocess.service.MyAccessibilityService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TEST_NOTIFY_ID = 0
    private val TEST_CHANNEL_ID = "test"
    private var mReceiverUser = ""
    private var mMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!hasPermission()){
            guideUserOpenPermission()
        }

        

        etUser.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                mReceiverUser = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        etMessage.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                mMessage = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        btnSend.setOnClickListener {
            pushNotification()
        }
    }

    private fun pushNotification(){
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: Notification.Builder = Notification.Builder(this)
            .setContentTitle("AutomatedProcess")
            .setContentText("receiver:$mReceiverUser,message:$mMessage")
            .setSmallIcon(R.mipmap.ic_launcher)
        val channel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel(
                TEST_CHANNEL_ID
                , "Notify Test"
                , NotificationManager.IMPORTANCE_HIGH
            )
            builder.setChannelId(TEST_CHANNEL_ID)
            manager.createNotificationChannel(channel)
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
        }
        manager.notify(TEST_NOTIFY_ID, builder.build())
    }

    private fun hasPermission() :Boolean{
        val enable = Settings.Secure.getInt(contentResolver,Settings.Secure.ACCESSIBILITY_ENABLED)
        if (enable == 1){
            val settingValue = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (!settingValue.isNullOrEmpty()){
                val splite = TextUtils.SimpleStringSplitter(':').apply { setString(settingValue) }
                splite.forEach {
                    if (it.contains(MyAccessibilityService::class.java.simpleName)){
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun guideUserOpenPermission(){
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
