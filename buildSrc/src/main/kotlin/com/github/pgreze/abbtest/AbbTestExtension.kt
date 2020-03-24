package com.github.pgreze.abbtest

import java.io.File

open class AbbTestExtension {

    var apkPath: File? = null

    lateinit var keystore: File

    lateinit var androidTestApkPath: File
}
