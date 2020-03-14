package com.github.pgreze.abbtest

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidBlackBoxTestPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("abbTest", AbbTestExtension::class.java)

//        project.extensions.getByType(AppExtension::class.java).apply {
//            defaultConfig {
//                applicationId = "abb-test"
//            }
//        }

        project.plugins.withType(AppPlugin::class.java) {
            val extension = project.extensions.getByType(AppExtension::class.java)
            project.setupTasks(extension)
        }
    }
}

private fun Project.setupTasks(appExtension: AppExtension) {
    val ext = project.extensions.getByType(AbbTestExtension::class.java)

    val buildToolsPath = appExtension.sdkDirectory
        .resolve("build-tools/${appExtension.buildToolsVersion}")

    appExtension.applicationVariants.all {
        if (name != "debug") return@all

        project.tasks.create("assembleAbbTest", DefaultTask::class.java) {
            dependsOn("mergeAndroidTestApk")
            description = "Assembles the black box test apk"
            group = "Build" // Same than assemble or assembleAndroidTest
        }

        project.tasks.create("mergeAndroidTestApk", AndroidTestApkMerge::class.java) {
            dependsOn("assembleDebug")

            baseApkPath = ext.androidTestApkPath
            testApkPath = projectDir.resolve("build/outputs/apk/debug/${project.name}-debug.apk")
            zipAlignPath = buildToolsPath.resolve("zipalign")
            keystore = ext.keystore
            apkSignerPath = buildToolsPath.resolve("apksigner")
            outputDir = projectDir.resolve("build/outputs/abbTest/")
        }
    }
}
