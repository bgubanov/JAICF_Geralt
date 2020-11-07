package com.justai.aimybox.jaicf

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.justai.aimybox.components.AimyboxAssistantFragment
import io.karn.notify.Notify
import kotlinx.android.synthetic.main.layout_activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val assistantFragment = AimyboxAssistantFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.assistant_container, assistantFragment)
            commit()
        }

        logo?.setOnClickListener {
            button?.visibility = View.VISIBLE
//            Toast.makeText(this, "qwertyui", Toast.LENGTH_LONG).show()
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmManager = ContextCompat.getSystemService(this, AlarmManager::class.java)
//                startJob()
            val handler = Handler()
            handler.postDelayed({
                Notify.with(this)
                    .header {
//                icon = R.mipmap.logo
                    }
                    .content { // this: Payload.Content.Default
                        title = "Эй!"
                        text = "Давай за работу!"
//                largeIcon = BitmapFactory.decodeResource(context.resources, R.mipmap.logo)
                    }
                    .show()
                alarmManager?.set(
                    AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                            + 7 * 1000, pendingIntent
                )
            }, 6000)

            Log.d("timer", "Таймер поставлен")
        }

        button?.setOnClickListener {
            val intent = Intent(this, AlarmReceiver::class.java)
            PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            ).cancel()
            button?.visibility = View.INVISIBLE
            val aimyboxButton: FrameLayout = findViewById(R.id.fragment_aimybox_assistant_button)
            aimyboxButton.callOnClick()
        }
    }

    override fun onBackPressed() {
        val assistantFragment = (supportFragmentManager.findFragmentById(R.id.assistant_container)
                as? AimyboxAssistantFragment)
        if (assistantFragment?.onBackPressed() != true) super.onBackPressed()
    }

    /*private fun startJob() {
        val TAG = "123"
        val componentName = ComponentName(this, ExampleJobService::class.java)
        val info = JobInfo.Builder(123, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 1000.toLong())
            .build()

        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled")
        } else {
            Log.d(TAG, "Job scheduling failed")
        }
    }*/
}