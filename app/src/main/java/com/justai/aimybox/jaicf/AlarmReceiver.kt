package com.justai.aimybox.jaicf

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import io.karn.notify.Notify
import kotlin.random.Random


class AlarmReceiver : BroadcastReceiver() {
    var counter = 0

    @SuppressLint("ShowToast")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Alarm!!!!!!", "")
        val v = getSystemService(context!!, Vibrator::class.java)

        val intent1 = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent1,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        counter++
        val alarmManager = getSystemService(context, AlarmManager::class.java)

        alarmManager?.set(
            AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + 700, pendingIntent
        )

        val listPhrases = listOf(
            "Давай за работу!", "Ты думал меня закрыть?",
            "Не смей меня игнорировать", "Я ещё жду", "Работай", "Ты где?",
            "Бицуха сама себя не накачает", "Упал, отжался"
        )
        Notify.with(context)
            .header {
//                icon = R.mipmap.logo
            }
            .content { // this: Payload.Content.Default
                title = "Эй!"
                text = listPhrases.random()
//                largeIcon = BitmapFactory.decodeResource(context.resources, R.mipmap.logo)
            }
            .show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v?.vibrate(1500)
        }
    }

}