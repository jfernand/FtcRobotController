package org.cr

import org.cr.State.*

enum class State {
    Start, Pressed, Released
}

/**
 * Detect a transition from 'pressed' to 'released' (true to false) across multiple checks.
 * Used to debounce the controller inputs buttons, but is more of a general debouncer.
 * It does not queue multiple events; that is, if multiple releases are detected in between
 * invocations of the wasPressed() function, only the first will be reported. This is not an
 * issue here, since we check() and call wasPressed() in every pass of the game loop.
 *
 * N.B.: functions with names like buttonWasPressed(), or isReady() are called "predicates",
 * short for "logical predicates." They indicate, by their name that their outcome is
 * true or false. Predicates should have no side effects.
 */
class PressableKey(val keyIsDown: () -> Boolean) {
    var state = Start

    fun check() {
        state = when (state) {
            Start -> if (keyIsDown()) {
                Pressed
            } else {
                Start
            }

            Pressed -> if (keyIsDown()) {
                Pressed
            } else {
                Released
            }

            Released -> Released
        }
    }

    fun buttonWasClicked(): Boolean {
        return if (state == Released) {
            state = Start
            true
        } else {
            false
        }
    }
}