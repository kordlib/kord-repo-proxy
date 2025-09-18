package dev.kord.maven.proxy

import dev.schlaubi.envconf.Config as EnvironmentConfig

object Config : EnvironmentConfig() {
    val PORT by getEnv(8080, String::toInt)
    val HOST by getEnv("::")

    val GITHUB_TOKEN by this

    val GITHUB_REPO by getEnv(listOf("kordlib", "kord")) { it.split('/') }
}
