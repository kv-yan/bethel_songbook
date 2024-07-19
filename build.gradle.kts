buildscript {
    val agp_version by extra("7.4.0")
    val agp_version1 by extra("7.2.0")
    val agp_version2 by extra("7.4.0")
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false
}
