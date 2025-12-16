package com.ucb.perritos.features.core.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ucb.perritos.MainActivity
import com.ucb.perritos.R
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Se ejecuta cuando Firebase le asigna un token nuevo a este celular
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("FCM TOKEN NUEVO: $token")
        // AQUÍ ES DONDE MÁS ADELANTE GUARDAREMOS EL TOKEN EN SUPABASE
        // guardarTokenEnSupabase(token)
    }

    // Se ejecuta cuando llega un mensaje y la app está ABIERTA (Foreground)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Si el mensaje trae datos para mostrar
        remoteMessage.notification?.let {
            mostrarNotificacion(it.title ?: "Perritos", it.body ?: "Tienes una alerta")
        }
    }

    private fun mostrarNotificacion(titulo: String, mensaje: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "perritos_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo_perrito) // ASEGÚRATE DE TENER UN ÍCONO PEQUEÑO
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal de notificación (Obligatorio para Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones Perritos",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(Random.Default.nextInt(), notificationBuilder.build())
    }
}