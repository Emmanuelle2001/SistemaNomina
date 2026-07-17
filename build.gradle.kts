// Top-level build file donde puedes agregar opciones de configuración comunes a todos los submódulos.
plugins {
    alias(libs.plugins.android.application) apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false // Centralizado aquí de forma global
}