package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowNotificationManager

//Version 1.2
@RunWith(RobolectricTestRunner::class)
class NotificationUnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)
    private val notificationManager = ShadowNotificationManager()

    init {
        notificationManager.setNotificationsEnabled(true)
        notificationManager.setNotificationPolicyAccessGranted(true)
    }

    @Test
    fun testShouldCheckNotificationVisibilityOnTimeExceed() {
        val activity = activityController.setup().get()
        val secondsToCount = 1

        activity.findViewById<Button>(R.id.settingsButton).performClick()
        val dialog = ShadowAlertDialog.getLatestAlertDialog()
        dialog.findViewById<EditText>(R.id.upperLimitEditText).setText("$secondsToCount")
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(secondsToCount * 1000 + 2100L)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        // TODO ShadowNotificationManager from robolectric does not works properly
        // So, we decided to check notifications through default NotificationManager
        val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = notificationManager.activeNotifications.find { it.id == 393939 }

        val message1 = "Could not find notification with id 393939. Did you set the proper id?"
        assertNotNull(message1, notification)

        val message2 = "The notification channel id does not equals \"org.hyperskill\""
        assertEquals(message2, "org.hyperskill", notification?.notification?.channelId)
    }
}
