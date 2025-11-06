import org.cr.PressableKey
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PressableKeyTests {
    @Test
    fun testPressableKey() {
        val sequence = SimulatePress(listOf(true, false))
        val key = PressableKey(sequence)
        assertFalse(key.buttonWasClicked(), "No press if no check")
        key.check()
        assertFalse(key.buttonWasClicked(), "No press when first depressed")
        key.check()
        assertTrue(key.buttonWasClicked(), "Press when released")
        assertFalse(key.buttonWasClicked(), "No press after check")
        key.check() // Test that we do not run out of presses.
    }
}

data class SimulatePress(var presses: List<Boolean>) : () -> Boolean {
    override fun invoke(): Boolean {
        if (presses.isEmpty()) return false
        val pressed = presses.first()
        presses = presses.drop(1)
        return pressed
    }
}