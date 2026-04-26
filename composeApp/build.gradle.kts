import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("app.cash.sqldelight") version "2.0.1"
}

kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation("app.cash.sqldelight:android-driver:2.0.1")

            implementation("io.insert-koin:koin-android:3.5.3")
            implementation("io.insert-koin:koin-androidx-compose:3.5.3")
        }

        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.coroutines.core)

            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-coroutines:1.1.1")

            implementation("app.cash.sqldelight:runtime:2.0.1")
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")

            implementation("io.insert-koin:koin-core:3.5.3")
            implementation("io.insert-koin:koin-compose:1.1.2")
        }

        iosMain.dependencies {
            implementation("app.cash.sqldelight:native-driver:2.0.1")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

sqldelight {
    databases {
        create("NotesDatabase") {
            packageName.set("com.example.demop4app.db")
        }
    }
}

android {
    namespace = "com.example.demop4app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.demop4app"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}