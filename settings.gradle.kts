dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "AdventOfCode"
include("2024")

