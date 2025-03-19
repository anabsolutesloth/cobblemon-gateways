plugins {
    base
    id("cobblemon-gateways.root-conventions")
}

val mavenGroup: String by rootProject
val modVersion: String by rootProject

group = mavenGroup
version = modVersion