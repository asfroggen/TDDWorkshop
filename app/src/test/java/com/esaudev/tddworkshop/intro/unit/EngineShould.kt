package com.esaudev.tddworkshop.intro.unit

import com.esaudev.tddworkshop.intro.Engine
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class EngineShould {
    private val engine = Engine()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `turn on`() = runTest {
        engine.turnOn()

        assertTrue(engine.isTurnedOn)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `turn off`() = runTest {
        engine.turnOff()

        assertFalse(engine.isTurnedOn)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `increase its temperature gradually when turned on`() = runTest {
        val flow = engine.turnOn()
        val actual = flow.toList()

        Assert.assertEquals(listOf(25, 50, 95), actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `decrease its temperature when turned off`() = runTest {
        engine.turnOn()
        engine.turnOff()

        Assert.assertEquals(15, engine.temperature)
    }
}