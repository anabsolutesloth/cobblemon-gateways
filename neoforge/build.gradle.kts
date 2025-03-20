plugins {
    id("cobblemon-gateways.platform-conventions")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

repositories {
    maven("https://maven.blamejared.com")
}

dependencies {
    "developmentNeoForge"(project(":common", configuration = "namedElements")) { isTransitive = false }
    bundle(project(":common", configuration = "transformProductionNeoForge")) { isTransitive = false }

    modApi(libs.architectury.neoforge)

    neoForge(libs.neoforge.loader)
    implementation(libs.neoforge.kotlin) {
        exclude("net.neoforged.fancymodloader", "loader")
    }

    modImplementation(libs.cobblemon.neoforge)

    //modImplementation("dev.shadowsoffire:GatewaysToEternity:${libs.minecraft.get().version}-5.0.2")
}

tasks {
    processResources {
        val neoforgeVersion = libs.neoforge.loader.get().version?.split('.')?.get(0)
        val forgeKotlinVersion = libs.neoforge.kotlin.get().version?.split('.')?.get(0)
        val architecturyVersion = libs.architectury.neoforge.get().version?.split('.')?.get(0)
        val cobblemonVersion = libs.cobblemon.neoforge.get().version?.split('+')?.get(0)

        inputs.property("minecraft_version", libs.minecraft.get().version)
        inputs.property("loader_version", neoforgeVersion)
        inputs.property("forge_kotlin_version", forgeKotlinVersion)
        inputs.property("architectury_version", architecturyVersion)
        inputs.property("cobblemon_version", cobblemonVersion)
        inputs.property("version", rootProject.version)
        inputs.property("mod_authors", rootProject.property("mod_authors"))
        inputs.property("mod_name", rootProject.property("mod_name"))
        inputs.property("mod_license", rootProject.property("mod_license"))
        inputs.property("mod_description", rootProject.property("mod_description"))
        inputs.property("mod_id", rootProject.property("mod_id"))

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(
                "version" to rootProject.version,
                "architectury_version" to architecturyVersion,
                "forge_kotlin_version" to forgeKotlinVersion,
                "loader_version" to neoforgeVersion,
                "minecraft_version" to libs.minecraft.get().version,
                "cobblemon_version" to cobblemonVersion,
                "mod_authors" to rootProject.property("mod_authors"),
                "mod_name" to rootProject.property("mod_name"),
                "mod_license" to rootProject.property("mod_license"),
                "mod_description" to rootProject.property("mod_description"),
                "mod_id" to rootProject.property("mod_id")
            )
        }
    }
}
