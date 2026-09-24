import io.github.kingsword09.symbolcraft.model.SymbolFill
import io.github.kingsword09.symbolcraft.model.SymbolVariant
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.symbolCraft)
}

kotlin {
    jvm()

    android {
        namespace = "com.github.rgbpx.chan.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
        }

        commonMain {
            kotlin {
                srcDir("src/commonMain/generated/symbols")
            }

            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.compose.material3.adaptive)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.okio)
                implementation(libs.androidx.datastore.core.okio)
                api(libs.androidx.datastore.core)
                implementation(libs.kermit)
                implementation(libs.filekit.core)
            }
        }
    }
}

symbolCraft {
    packageName.set("com.github.rgbpx.chan.symbols")
    outputDirectory.set("src/commonMain/generated/symbols")

    naming {
        pascalCase()
    }

    materialSymbols("content_copy", "reset_settings") {
        style(
            weight = 400,
            variant = SymbolVariant.OUTLINED,
            fill = SymbolFill.UNFILLED,
        )
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
