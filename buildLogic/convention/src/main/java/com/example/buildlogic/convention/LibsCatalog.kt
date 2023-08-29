package com.example.buildlogic.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

fun Project.getLibsCatalog() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun DependencyHandlerScope.implementation(catalog: VersionCatalog, libraryName: String) {
    add("implementation", catalog.findLibrary(libraryName).get())
}

fun DependencyHandlerScope.implementation(project: Project) {
    add("implementation", project)
}