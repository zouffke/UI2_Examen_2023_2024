// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false

  //serialization
  alias(libs.plugins.jetbrains.kotlin.serialization) apply false
  //hilt
  alias(libs.plugins.jetbrains.kotlin.kapt) apply false
  alias(libs.plugins.hilt.android) apply false
}