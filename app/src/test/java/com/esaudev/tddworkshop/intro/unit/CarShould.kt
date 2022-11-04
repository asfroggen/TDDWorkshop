package com.esaudev.tddworkshop.intro.unit

import com.esaudev.tddworkshop.intro.Car
import com.esaudev.tddworkshop.intro.Engine
import com.esaudev.tddworkshop.utils.MainCoroutineRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarShould {

    private var engine: Engine = mock()

    @get:Rule
    var coroutinesTestRule = MainCoroutineRule()

    private val car: Car

    init {
       runTest {
           whenever(engine.turnOn()).thenReturn(flow {
               delay(2000)
               emit(25)
               delay(2000)
               emit(50)
               delay(2000)
               emit(95)
           })
       }

       car = Car(
            fuel = 5.0,
            engine = engine
       )
    }

    @Test
    fun `loose fuel when it turn on`() = runTest{
        car.turnOn()

        assertEquals(4.5, car.fuel)
    }

    @Test
    fun `turn on its engine`() = runTest {
        car.turnOn()
        advanceTimeBy(6001)
        verify(engine, times(1)).turnOn()
    }

}