package com.esaudev.tddworkshop.intro

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Car(
    var fuel: Double,
    var engine: Engine
) {

    fun turnOn() {
        fuel -= 0.5
        CoroutineScope(Dispatchers.Main).launch {
            engine.turnOn().collect {
                Log.d("COURSE", "Collected engine temperature: $it")
            }
        }
    }
}