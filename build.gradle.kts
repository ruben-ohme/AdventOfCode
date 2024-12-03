plugins {
    kotlin("jvm") version "2.1.0"
}

dependencies {
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

