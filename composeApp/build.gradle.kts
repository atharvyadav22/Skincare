import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)

    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.buildkonfig)

    // 1. Mokkery Compiler Plugin (for Mocking)
    id("dev.mokkery") version "3.3.0"

}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.tab.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.auth)

            implementation(libs.multiplatform.settings)
            implementation(libs.kermit)
            implementation(libs.compottie)
            implementation(libs.compottie.dot)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))

            // 2. Coroutines Control (gives you runTest and unconfined dispatchers)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")


            // 4. Flow Testing (The standard for testing Kotlin Flows)
            implementation("app.cash.turbine:turbine:1.2.1")
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            implementation(libs.ktor.client.cio)
        }
    }
}

android {
    namespace = "org.aystudios.skincare"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.aystudios.skincare"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.aystudios.skincare.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.aystudios.skincare"
            packageVersion = "1.0.0"
        }
    }
}

val props = Properties()
props.load(rootProject.file("local.properties").inputStream())

buildkonfig {
    packageName = "org.aystudios.skincare.config"


    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "API_KEY", props.getProperty("API_KEY"))
    }
}


// 7. (Optional but recommended) Configure Kover to ignore generated code
//kover {
//    reports {
//        filters {
//            excludes {
//                classes(
//                    // Exclude generated DI code, auto-generated build configs, etc.
//                    "*_Factory",
//                    "**.*_Provide*Factory",
//                    "**.*Builder",
//                    "**.*Dagger*"
//                )
//            }
//        }
//    }
//}
