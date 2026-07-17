plugins {
    alias(libs.plugins.android.application)
    // Nuevo plugin obligatorio de Compose para Kotlin 2.0+[cite: 1]
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.sistemanomina"

    // OBLIGATORIO PARA LA OPCIÓN A: Compilar contra el SDK 37
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.sistemanomina"
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // OBLIGATORIO: Habilitar Jetpack Compose[cite: 1]
    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Implementación del ecosistema de Jetpack Compose (Usando el BOM)[cite: 1]
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Librerías obligatorias para la práctica (Navegación, ViewModel y Retrofit)[cite: 1]
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Dependencias base originales de tu proyecto
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Bloque de pruebas (Testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}