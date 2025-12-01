plugins {
    kotlin("jvm") version "2.2.21"
}

dependencies {
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}
