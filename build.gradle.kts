buildscript {
    val agp_version by extra("7.4.0")
    val agp_version1 by extra("7.2.0")
    val agp_version2 by extra("7.4.0")
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}