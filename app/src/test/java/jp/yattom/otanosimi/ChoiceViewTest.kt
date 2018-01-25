package jp.yattom.otanosimi

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ChoiceViewTest {
    class DragTrackTest {
        @Test
        fun drag_short() {
            val sut = ChoiceView.DragTrack()
            assertFalse(sut.dragging)
            sut.start(0f, 0f)
            sut.moveTo(10f, 20f)
            assertTrue(sut.dragging)
            assertEquals(10f, sut.centerX)
            assertEquals(20f, sut.centerY)
        }

        @Test
        fun drag_several() {
            val sut = ChoiceView.DragTrack()
            assertFalse(sut.dragging)
            sut.start(0f, 0f)
            sut.moveTo(10f, 20f)
            sut.moveTo(20f, 0f)
            sut.moveTo(30f, 10f)
            sut.end()
            assertFalse(sut.dragging)
            assertEquals(30f, sut.centerX)
            assertEquals(10f, sut.centerY)
        }

        @Test
        fun drag_relative_several() {
            val sut = ChoiceView.DragTrack()
            assertFalse(sut.dragging)
            sut.start(100f, 200f)
            sut.moveTo(110f, 220f)
            sut.moveTo(120f, 200f)
            sut.moveTo(130f, 210f)
            sut.end()
            assertFalse(sut.dragging)
            assertEquals(30f, sut.centerX)
            assertEquals(10f, sut.centerY)
        }

        @Test
        fun drag_release_drag() {
            val sut = ChoiceView.DragTrack()
            assertFalse(sut.dragging)
            sut.start(100f, 200f)
            sut.moveTo(110f, 220f)
            sut.end()
            sut.start(200f, 300f)
            sut.moveTo(215f, 325f)
            sut.end()
            assertFalse(sut.dragging)
            assertEquals(10f + 15f, sut.centerX)
            assertEquals(20f + 25f, sut.centerY)
        }
    }
}
