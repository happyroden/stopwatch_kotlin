package org.hyperskill.stopwatch

import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

//Version 1.2
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    @Test
    fun testShouldCheckStartButtonExist() {
        val activity = activityController.setup().get()

        val message = "does view with id \"startButton\" placed in activity?"
        assertNotNull(message, activity.findViewById<Button>(R.id.startButton))
    }

    @Test
    fun testShouldCheckResetButtonExist() {
        val activity = activityController.setup().get()

        val message = "does view with id \"resetButton\" placed in activity?"
        assertNotNull(message, activity.findViewById<Button>(R.id.resetButton))
    }

    @Test
    fun testShouldCheckTextViewExist() {
        val activity = activityController.setup().get()

        val message = "does view with id \"textView\" placed in activity?"
        assertNotNull(message, activity.findViewById<TextView>(R.id.textView))
    }

    @Test
    fun testShouldCheckStartButtonText() {
        val activity = activityController.setup().get()

        val message = "in button property \"text\""
        assertEquals(message, "Start", activity.findViewById<Button>(R.id.startButton).text)
    }

    @Test
    fun testShouldCheckResetButtonText() {
        val activity = activityController.setup().get()

        val message = "in button property \"text\""
        assertEquals(message, "Reset", activity.findViewById<Button>(R.id.resetButton).text)
    }

    @Test
    fun testShouldCheckProgressBarInvisibilityOnInit() {
        val activity = activityController.setup().get()

        val message = "invalid progress bar visibility"
        assertNotEquals(message, View.VISIBLE, activity.findViewById<View>(R.id.progressBar).visibility)
    }

    @Test
    fun testShouldCheckProgressBarVisibilityOnStart() {
        val activity = activityController.setup().get()

        activity.findViewById<Button>(R.id.startButton).performClick()

        val message = "invalid progress bar visibility"
        assertEquals(message, View.VISIBLE, activity.findViewById<View>(R.id.progressBar).visibility)
    }

    @Test
    fun testShouldCheckProgressBarInvisibilityOnReset() {
        val activity = activityController.setup().get()
        activity.findViewById<Button>(R.id.startButton).performClick()
        val message = "invalid progress bar visibility"
        assertEquals(message, View.VISIBLE, activity.findViewById<View>(R.id.progressBar).visibility)
        activity.findViewById<Button>(R.id.resetButton).performClick()
        assertNotEquals(message, View.VISIBLE, activity.findViewById<View>(R.id.progressBar).visibility)
    }

    @Test
    fun testShouldCheckProgressBarColorEachSecond10() {
        val message = "invalid progress bar color"

        val activity = activityController.setup().get()

        activity.findViewById<Button>(R.id.startButton).performClick()

        Thread.sleep(100L)
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        var lastColor: Int? = 0
        for (i in 0 until 10) {
            Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()
            val color = activity.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList?.defaultColor
            assertNotEquals(message, color, lastColor)
            lastColor = color

            Thread.sleep(1000L)
            Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()
        }
    }
}