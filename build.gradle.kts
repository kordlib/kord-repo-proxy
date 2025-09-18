plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.envconf)
    implementation(libs.slf4j.simple)
}

application {
    mainClass = "dev.kord.maven.proxy.ApplicationKt"
}
