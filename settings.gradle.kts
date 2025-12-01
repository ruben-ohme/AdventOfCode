dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "AdventOfCode"
include("2024", "2025")
