package com.wpfl5.chattutorial.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.ui.splash.SplashActivity


class MyFbMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) { //토큰이 변경되었을때 호출
        super.onNewToken(token)
        //v17.0.0 이후부터는 onTokenRefresh()-depriciated
        Log.d("//token", token)
    }




    /*
        푸시알림을 받았을때 호출되는 메소드입니다.
        장기작업일때 schedulejob() 메소드를 호출합니다.
        10초이내 메시지 처리가 가능할 경우 handleNow() 를 호출합니다.
        sendNotification(body)메소드로 메시지의 body를 넘겨줍니다.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) { //푸시알림을 받았을때 호출되는 메소드
        Log.d(TAG, "From: ${remoteMessage.from}")

        if(remoteMessage.notification != null){
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            val messageBody = remoteMessage.notification?.body
            val messageTitle = remoteMessage.notification?.title

            sendNotification(messageTitle!!, messageBody!!)
        }

//        remoteMessage.data.isNotEmpty().let {
//            Log.d(TAG, "Message data payload: " + remoteMessage.data)
//            if (true) {/* 장기 실행 작업으로 데이터를 처리해야하는지 확인*/
//                //장기 실행 작업 (10 초 이상)의 경우 Firebase Job Dispatcher를 사용하십시오
//                scheduleJob()
//            } else {
//                //10 초 이내에 메시지 처리
//                handleNow()
//            }
//        }
//
//        // 메시지에 알림 페이로드가 포함되어 있는지 확인하십시오.
//        remoteMessage.notification?.let {
//            Log.d(TAG, "Message Notification Body: ${it.body}")
//            sendNotification(it.title!!, it.body!!)
//        }
    }

    /*
        푸시알람을 만들어주는 메소드입니다.
        PendingIntent에 알람을 받을시 넘어갈 화면을 지정합니다.
        channelId 는 수신 메시지에 명시적으로 설정된 알림 채널이 없다면 이값이 쓰입니다.
        넘겨받은 body로 notificationCompat.Builder로 알림을 만들어줍니다.
     */
    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent : PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )


        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // android Oreo 알림 채널이 필요합니다
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /*알림ID */, notificationBuilder.build())
    }


    /*
        장기 작업(10초이상)일때는 scheduleJob() 메소드
        그 이하로 처리가능한 작업일때는 handleNow()에 추가적으로 처리할 내용을 작성해줍니다.
     */
    private fun scheduleJob() { //장기작업인지(10초이상)일때 처리하는 메소드
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val myJob = dispatcher.newJobBuilder()
            .setService(MyJobService::class.java)
            .setTag("my-job-tag")
            .build()
        dispatcher.schedule(myJob)
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    companion object {
        private const val TAG = "FirebaseMessageService"
    }
}