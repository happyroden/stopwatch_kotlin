package org.hyperskill.stopwatch

import android.os.Looper
import android.widget.Button
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

//Version 1.2
@RunWith(RobolectricTestRunner::class)
class TimerStateUnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    @Test
    fun testShouldCheckTimerInitialValue() {
        val activity = activityController.setup().get()

        val message = "in TextView property \"text\""
        assertEquals(message, "00:00", activity.findViewById<TextView>(R.id.textView).text)
    }

    @Test
    fun testShouldStartCountOnStartButtonClick() {
        val activity = activityController.setup().get()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(1100)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        val message = "in TextView property \"text\""
        assertEquals(message, "00:01", activity.findViewById<TextView>(R.id.textView).text)
    }

    @Test
    fun testShouldStopTimerAndResetCountOnResetButtonClick() {
        val activity = activityController.setup().get()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(1100)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        activity.findViewById<Button>(R.id.resetButton).performClick()

        val message = "in TextView property \"text\""
        assertEquals(message, "00:00", activity.findViewById<TextView>(R.id.textView).text)
    }

    @Test
    fun testShouldContinueCountOnPressingStartButtonAgain() {
        val activity = activityController.setup().get()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(1100)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(1100)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        val message = "in TextView property \"text\""
        assertEquals(message, "00:02", activity.findViewById<TextView>(R.id.textView).text)
    }

    @Test
    fun testShouldIgnorePressingResetButtonAgain() {
        val activity = activityController.setup().get()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(1100)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        activity.findViewById<Button>(R.id.resetButton).performClick()

        Thread.sleep(1100)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        activity.findViewById<Button>(R.id.resetButton).performClick()

        Thread.sleep(1100)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        val message = "in TextView property \"text\""
        assertEquals(message, "00:00", activity.findViewById<TextView>(R.id.textView).text)
    }
}