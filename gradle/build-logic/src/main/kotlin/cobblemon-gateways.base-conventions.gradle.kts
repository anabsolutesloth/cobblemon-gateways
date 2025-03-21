import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    java
    `java-library`
    kotlin("jvm")
    id("architectury-plugin")
    id("dev.architectury.loom")
}

repositories {
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.shadowsoffire.dev/releases/")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
}

kotlin {
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

architectury {
    minecraft = libs.minecraft.get().version!!
}

@Suppress("UnstableApiUsage")
loom {
    silentMojangMappingsLicense()
    mixin {
        useLegacyMixinAp.set(true)
        defaultRefmapName.set("cobblemon-gateways-${project.name}-refmap.json")
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        loom.officialMojangMappings()
        parchment(libs.parchment)
    })

    testRuntimeOnly(libs.junit.launcher)
    testImplementation(libs.junit.engine)
    testImplementation(libs.mockk)
}

tasks {
    withType<Test> {
        useJUnitPlatform()
        maxHeapSize = "1G"
    }

    withType<JavaCompile> {
        options.release.set(21)
    }

    withType<Jar> {
        from(rootProject.file("LICENSE"))
    }
}
