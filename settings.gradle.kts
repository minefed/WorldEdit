pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "worldedit"

// Fabric distributions need only core and their shaded libraries, not other platforms.
val minefedPlatform = providers.gradleProperty("minefedPlatform").orNull
require(minefedPlatform == null || minefedPlatform == "fabric") {
    "minefedPlatform must be fabric when specified"
}

include("worldedit-libs")

if (minefedPlatform == null) {
    listOf("1.17.1", "1.18.2", "1.19.4", "1.20", "1.20.2", "1.20.4").forEach {
        include("worldedit-bukkit:adapters:adapter-$it")
    }
}

val platforms = if (minefedPlatform == "fabric") listOf("core", "fabric")
    else listOf("bukkit", "core", "sponge", "fabric", "forge", "cli")
platforms.forEach {
    include("worldedit-libs:$it")
    include("worldedit-$it")
}
if (minefedPlatform == null) {
    include("worldedit-mod")
}
include("worldedit-libs:core:ap")

if (minefedPlatform == null) {
    include("worldedit-core:doctools")
    include("verification")
}
