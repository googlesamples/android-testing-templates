app_build:
	./gradlew app:assemble{,AndroidTest}

app_install:
	adb install -r app/build/outputs/apk/flavor1/debug/app-flavor1-debug.apk

abb_build:
	./gradlew module-abb-test:assembleAbbTest

abb_install:
	adb install -r module-abb-test/build/outputs/abbTest/abb-test.apk

abb_run:
	adb shell am instrument -w -r -e debug false -e class 'com.example.android.testing.blueprint.bbtest.AbbTest' com.example.android.testing.blueprint.flavor1.test/androidx.test.runner.AndroidJUnitRunner
