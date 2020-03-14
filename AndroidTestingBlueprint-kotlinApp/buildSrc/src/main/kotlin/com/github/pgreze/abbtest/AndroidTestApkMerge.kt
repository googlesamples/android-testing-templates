package com.github.pgreze.abbtest

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

open class AndroidTestApkMerge : DefaultTask() {

    companion object {
        const val APK_PREFIX = "abb-test"
    }

    override fun getDescription(): String =
        "Merge tests dex into target APK"

    @get:InputFile
    lateinit var baseApkPath: File

    @get:InputFile
    lateinit var testApkPath: File

    @get:InputFile
    lateinit var zipAlignPath: File

    @get:InputFile
    lateinit var keystore: File

    @get:InputFile
    lateinit var apkSignerPath: File

    @get:OutputDirectory
    lateinit var outputDir: File

    @TaskAction
    fun invoke() {
        outputDir.mkdirs()

        val unalignedApk = outputDir.resolve("$APK_PREFIX-unaligned.apk")

        ZipOutputStream(FileOutputStream(unalignedApk)).use { outputZip ->
            // Append all files from original APK + count existing dex files
            var dexCount = ZipFile(baseApkPath).use { zipFile ->
                zipFile.entries().asSequence().count { entry ->
                    outputZip.addEntry(entry.name, entry, zipFile)
                    return@count entry.isDex
                }
            }

            // Append all dex from the local apk
            ZipFile(testApkPath).use { zipFile ->
                zipFile.entries().asSequence().filter(ZipEntry::isDex).forEach { entry ->
                    // Add in output zip
                    outputZip.addEntry("classes${++dexCount}.dex", entry, zipFile)
                }
            }
        }

        val alignedApk = outputDir.resolve("$APK_PREFIX.apk")

        // Align
        exec("$zipAlignPath -f 4 $unalignedApk $alignedApk")
        unalignedApk.delete()
        // Sign
        val ksCredentials = "--ks-key-alias androiddebugkey --ks-pass pass:android --key-pass pass:android"
        exec("$apkSignerPath sign --ks $keystore $ksCredentials $alignedApk")
    }
}

private fun ZipOutputStream.addEntry(
    name: String,
    entry: ZipEntry,
    zipFile: ZipFile
) {
    putNextEntry(ZipEntry(name))
    write(zipFile.getInputStream(entry).readBytes())
    closeEntry()
}

private fun exec(command: String) {
    val proc = Runtime.getRuntime().exec(command)
    val error = proc.errorStream.use { BufferedReader(InputStreamReader(it)).readText() }
    if (proc.waitFor() != 0) {
        throw RuntimeException("Error while running ${command.split(" ").first()}: $error")
    }
}

private val ZipEntry.isDex: Boolean
    get() = name.endsWith(".dex")
