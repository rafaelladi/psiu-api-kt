package com.dietrich.psiuapikt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class PsiuApiKtApplication

fun main(args: Array<String>) {
    runApplication<PsiuApiKtApplication>(*args)
}
