package org.cr

enum class State {
    Start, Pressed, Released
}

class PressableKey(val down: () -> Boolean) {
    var state = State.Start

    fun check() {
        state = when (state) {
            State.Start -> if (down()) {
                State.Pressed
            } else {
                State.Start
            }

            State.Pressed -> if (down()) {
                State.Pressed
            } else {
                State.Released
            }

            State.Released -> State.Released
        }
    }

    fun wasPressed(): Boolean {
        return if (state == State.Released) {
            state = State.Start
            true
        } else {
            false
        }
    }
}