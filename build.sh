#!/bin/bash
cd /d/Agent/Xingkong
export JAVA_HOME=/d/Agent/tools/jdk21
export ANDROID_HOME=/c/Users/Pointers/AppData/Local/Android/Sdk
export ANDROID_NDK_HOME=/d/Agent/tools/android-ndk-r27c
export PATH="$JAVA_HOME/bin:$PATH"
echo "=== Java 版本 ==="
java -version 2>&1 | head -1
echo "=== 开始 gradlew assembleDebug ==="
./gradlew assembleDebug --no-daemon --warning-mode=summary 2>&1
echo "=== exit code: $? ==="
