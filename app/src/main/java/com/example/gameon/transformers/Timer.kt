package com.example.gameon.transformers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class Timer(private val initialTime: String) {

    private val _elapsedTime = MutableStateFlow(initialTime)
    val elapsedTime: StateFlow<String> = _elapsedTime

    private var job: Job? = null

    fun start() {
        if (initialTime == "Match Ended") {
            _elapsedTime.value = initialTime
            return
        }
        job = CoroutineScope(Dispatchers.Default).launch {
            val (minutes, seconds) = initialTime.split(":").map { it.toInt() }
            var totalSeconds = minutes * 60 + seconds

            while (isActive) {
                delay(1_000)
                totalSeconds++

                val updatedMinutes = totalSeconds / 60
                val updatedSeconds = totalSeconds % 60

                _elapsedTime.value = String.format("%02d:%02d", updatedMinutes, updatedSeconds)
            }
        }
    }

    fun stop() {
        job?.cancel()
    }

}