import org.cr.PressableKey
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PressableKeyTests {
    @Test
    fun testPressableKey() {
        val sequence = SimulatePress(listOf(true, false))
        val key = PressableKey(sequence)
        assertTrue(!key.wasPressed(), "No press if no check")
        key.check()
        assertFalse(key.wasPressed(), "No press when first depressed")
        key.check()
        assertTrue(key.wasPressed(), "Press when released")
        assertFalse(key.wasPressed(), "No press after check")
    }
}

data class SimulatePress(var presses: List<Boolean>) : () -> Boolean {
    override fun invoke(): Boolean {
        val pressed = presses.first()
        presses = presses.drop(1)
        return pressed
    }
}