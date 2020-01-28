package com.lamti.alarmy.domain.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lamti.alarmy.domain.services.AlarmyNotificationService
import com.lamti.alarmy.data.models.Alarm
import com.lamti.alarmy.ui.AlarmyActivity
import com.lamti.alarmy.ui.main_activity.MainActivity
import com.lamti.alarmy.domain.utils.ALARM_DATA_EXTRA
import java.util.*

class AlarmyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val cAlarm = getAlarm(intent) ?: return

        val aca = Calendar.getInstance()
        aca.timeInMillis = cAlarm.miliTime
        val alarmHour = aca.get(Calendar.HOUR_OF_DAY)
        val alarmMinute = aca.get(Calendar.MINUTE)

        val nowCal = Calendar.getInstance()
        val nowHour = nowCal.get(Calendar.HOUR_OF_DAY)
        val nowMinute = nowCal.get(Calendar.MINUTE)

        if (nowHour == alarmHour && nowMinute == alarmMinute) {
            val json = alarmToJsonMapper(cAlarm)
            AlarmyNotificationService.startService(
                context, "Foreground Service is running...", json
            )
        } else {
            AlarmyNotificationService.startService(
                context, "Foreground Service is running...", null
            )
        }
    }

    private fun getAlarm(intent: Intent?): Alarm? {
        val stringLocation = intent?.getStringExtra(ALARM_DATA_EXTRA)
        val type = object : TypeToken<Alarm>() {}.type
        return if (stringLocation == null)
            null
        else
            Gson().fromJson(stringLocation, type)
    }

    private fun alarmToJsonMapper(alarm: Alarm): String {
        val type = object : TypeToken<Alarm>() {}.type
        return Gson().toJson(alarm, type)
    }

    private fun launchAlarmyActivity(context: Context?, alarm: Alarm) {
        val alarmyIntent = Intent(context, AlarmyActivity::class.java)
        val type = object : TypeToken<Alarm>() {}.type
        val json = Gson().toJson(alarm, type)
        alarmyIntent.putExtra(ALARM_DATA_EXTRA, json)
        alarmyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(alarmyIntent)
    }

    private fun launchMainActivity(context: Context?) {
        val mainIntent = Intent(context, MainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(mainIntent)
    }

}