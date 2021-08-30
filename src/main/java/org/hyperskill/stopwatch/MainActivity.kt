package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.RED
import android.os.Build
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.renderscript.ScriptGroup.Binding
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*


class MainActivity : AppCompatActivity() {

    val colorGenerator: () -> Int = colorGeneratorFactory()

    var limit  = 0

    private var notificationId = 393939

    private var CHANNEL_ID = "org.hyperskill"

//
//
//    val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val textView: TextView by lazy {
        findViewById<TextView>(R.id.textView)
    }
    private val progressBar: ProgressBar by lazy {
        this.findViewById<ProgressBar>(R.id.progressBar)
    }
    private val settingsButton: Button by lazy {
        findViewById<Button>(R.id.settingsButton)
    }
    private val my_text_: TextView by lazy {
        this.findViewById(R.id.my_text_)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                this.description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }


    private fun sendNotification() {
        val intent: Intent = Intent(this, MainActivity::class.java).apply {
             flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        var pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notification")
            .setContentText("limit exceeded")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
            }
    }

    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        my_text_.text = "I love view binding"

        }
    var coloor: Boolean = false

    fun startButtonClicked(view: View) {
        if(timer == null) {
            progressBar.visibility = View.VISIBLE
            settingsButton.isEnabled = false
            timer = Timer()
            timer?.scheduleAtFixedRate(createdNewTimerTask(), 0L, 1000L)

        }
    }

    fun resetButtonClicked(view: View) {
        progressBar.visibility = View.INVISIBLE
        settingsButton.isEnabled = true
        textView.text = "00:00"
        textView.setTextColor(Color.BLACK)
        timer?.cancel()
        timer = null
    }

    fun settingsButtonClicked(view: View) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_time, null)

        dialogBuilder
            .setView(dialogLayout)
            .setNegativeButton("Cancel")  {
                    dialog, which ->  dialog.dismiss()
            }.setPositiveButton("OK") { dialog, which ->
                val textInput = dialogLayout.findViewById<EditText>(R.id.upperLimitEditText)
                val numberInput = textInput.text.toString().toIntOrNull() ?: 0
                limit = numberInput
                dialog.dismiss()
            }.show()

    }

    private fun createdNewTimerTask(): TimerTask {
        return object : TimerTask() {

            val startTime = System.currentTimeMillis()
            val immutableLimit = limit

            override fun run() {

                val currentTime = System.currentTimeMillis()
                val elapsed = (currentTime - startTime) / 1000
                val seconds = String.format("%02d", elapsed % 60)
                val minutes = String.format("%02d", elapsed / 60)

                runOnUiThread {
                    if(immutableLimit > 0 && elapsed > immutableLimit) {
                        textView.setTextColor(Color.RED)
                        coloor = true
                    }
                    if (coloor==true) {
                        sendNotification()
                    }
                    progressBar.indeterminateTintList = ColorStateList.valueOf(colorGenerator())
                    textView.text = "$minutes:$seconds"


                }
            }
        }
    }

    private fun colorGeneratorFactory() : () -> Int {

        var currentIndex = 0
        val colorList = listOf(
            Color.BLUE,
            Color.DKGRAY,
            Color.YELLOW,
            Color.GRAY,
            Color.MAGENTA,
            Color.BLACK,
            Color.GREEN,
            Color.LTGRAY
        )

        return {
            currentIndex = (currentIndex + 1) % colorList.size
            colorList[currentIndex]
        }
    }
}