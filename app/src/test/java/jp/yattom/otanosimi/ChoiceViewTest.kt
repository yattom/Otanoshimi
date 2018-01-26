package jp.yattom.otanosimi

import android.graphics.RectF
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

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

    class CircleGroupTest {
        var viewPort: RectF = RectF(0f, 0f, 0f, 0f)  // dummy initialization

        @BeforeEach
        fun setUp() {
            val v = mock(RectF::class.java)
            Mockito.`when`(v.width()).thenReturn(500f)
            Mockito.`when`(v.height()).thenReturn(500f)
            viewPort = v
        }

        @Test
        fun three_choices() {
            val sut = ChoiceView.CirclrGroup(viewPort, 3)
            sut.setViewPortCenter(0f, 0f)
            assertEquals(3, sut.circles.size)
            assertTrue(sut.circles[0].x == 0f, "first circle is at the top")
            assertTrue(sut.circles[0].y < -250f, "first circle is at the top")
            assertTrue(sut.circles[1].x > 250, "second circle is at the bottom right")
            assertTrue(sut.circles[1].y > 0, "second circle is at the bottom right")
            assertTrue(sut.circles[2].x < -250f, "second circle is at the bottom left")
            assertTrue(sut.circles[2].y > 0, "second circle is at the bottom left")
        }
    }
}
