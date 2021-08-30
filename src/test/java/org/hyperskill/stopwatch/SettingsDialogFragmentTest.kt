package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.graphics.Color
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowAlertDialog

//Version 1.2
@RunWith(RobolectricTestRunner::class)
class SettingsDialogFragmentTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    @Test
    fun testShouldCheckSettingsButtonExist() {
        val activity = activityController.setup().get()

        val message = "does view with id \"settingsButton\" placed in activity?"
        Assert.assertNotNull(message, activity.findViewById<Button>(R.id.settingsButton))
    }

    @Test
    fun testShouldCheckSettingsButtonEnable() {
        val activity = activityController.setup().get()

        val message1 = "view with id \"settingsButton\" should be enabled when timer stopped"
        Assert.assertTrue(message1, activity.findViewById<Button>(R.id.settingsButton).isEnabled)

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(1000L)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        val message2 = "view with id \"settingsButton\" should be disabled when timer runs"
        Assert.assertFalse(message2, activity.findViewById<Button>(R.id.settingsButton).isEnabled)

        activity.findViewById<Button>(R.id.resetButton).performClick()
        Assert.assertTrue(message1, activity.findViewById<Button>(R.id.settingsButton).isEnabled)
    }

    @Test
    fun testShouldShowAlertDialogOnSettingsButtonClick() {
        val activity = activityController.setup().get()

        activity.findViewById<Button>(R.id.settingsButton).performClick()
        val dialog = ShadowAlertDialog.getLatestAlertDialog()
        Assert.assertNotNull("Does dialog shows on \"settingsButton\" click?", dialog)
    }

    @Test
    fun testShouldCheckLimitDoesNotSetupsOnCancel() {
        val activity = activityController.setup().get()
        val secondsToCount = 2

        activity.findViewById<Button>(R.id.settingsButton).performClick()
        val dialog = ShadowAlertDialog.getLatestAlertDialog()
        dialog.findViewById<EditText>(R.id.upperLimitEditText).setText("$secondsToCount")
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).performClick()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(secondsToCount * 1000 + 500L)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        val message = "\"textView\" color should not be RED"
        Assert.assertNotEquals(message, Color.RED, activity.findViewById<TextView>(R.id.textView).currentTextColor)
    }

    @Test
    fun testShouldCheckLimitSetupsOnOk() {
        val activity = activityController.setup().get()
        val secondsToCount = 2

        activity.findViewById<Button>(R.id.settingsButton).performClick()
        val dialog = ShadowAlertDialog.getLatestAlertDialog()
        dialog.findViewById<EditText>(R.id.upperLimitEditText).setText("$secondsToCount")
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(secondsToCount * 1000 + 1100L)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        val message = "\"textView\" color should be RED"
        Assert.assertEquals(message, Color.RED, activity.findViewById<TextView>(R.id.textView).currentTextColor)
    }

    @Test
    fun testShouldCheckColorsOnRestart() {
        val activity = activityController.setup().get()
        val secondsToCount = 2

        activity.findViewById<Button>(R.id.settingsButton).performClick()
        val dialog = ShadowAlertDialog.getLatestAlertDialog()
        dialog.findViewById<EditText>(R.id.upperLimitEditText).setText("$secondsToCount")
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(secondsToCount * 1000 + 1100L)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        val message1 = "\"textView\" color should be RED"
        Assert.assertEquals(message1, Color.RED, activity.findViewById<TextView>(R.id.textView).currentTextColor)

        activity.findViewById<Button>(R.id.resetButton).performClick()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(1100L)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        val message2 = "\"textView\" color should not be RED"
        Assert.assertNotEquals(message2, Color.RED, activity.findViewById<TextView>(R.id.textView).currentTextColor)
    }
}