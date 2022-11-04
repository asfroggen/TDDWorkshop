package com.esaudev.tddworkshop.intro.acceptancetests


import com.esaudev.tddworkshop.intro.Car
import com.esaudev.tddworkshop.intro.Engine
import com.esaudev.tddworkshop.utils.MainCoroutineRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarFeature {

    private var engine = Engine()

    @get:Rule
    var coroutinesTestRule = MainCoroutineRule()

    private var car = Car(
        fuel = 6.0,
        engine = engine
    )

    @Test
    fun `car lose fuel when turns on`() = runTest {
        car.turnOn()

        assertEquals(5.5, car.fuel)
    }

    @Test
    fun `car increases its temperature gradually when turns on`() = runTest {
        car.turnOn()

        advanceTimeBy(2001)
        assertEquals(25, car.engine.temperature)
        advanceTimeBy(2001)
        assertEquals(50, car.engine.temperature)
        advanceTimeBy(2001)
        assertEquals(95, car.engine.temperature)

        assertEquals(true, car.engine.isTurnedOn)
    }
}