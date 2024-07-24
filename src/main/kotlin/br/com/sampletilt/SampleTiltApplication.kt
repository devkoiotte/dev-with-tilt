package br.com.sampletilt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleTiltApplication

fun main(args: Array<String>) {
    runApplication<SampleTiltApplication>(*args)
}
