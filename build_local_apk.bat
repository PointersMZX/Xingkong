@echo off
setlocal EnableExtensions
rem ============================================================
rem  星控 Xingkong 本地一键打包（不依赖 GitHub CI）
rem  改完 work\xingkong-src 里的 smali/资源后，双击本脚本即可出 apk
rem  产物: release\Xingkong_v1.0.0.apk
rem ============================================================
set "SRC=D:\Agent\Xingkong\work\xingkong-src"
set "APKTOOL=D:\Agent\tools\downloads\apktool.jar"
set "JAVA=D:\Agent\tools\jdk21\bin\java.exe"
set "BT=C:\Users\Pointers\AppData\Local\Android\Sdk\build-tools\34.0.0"
set "KEY=D:\Agent\Xingkong\app\debug.keystore"
set "OUT=D:\Agent\Xingkong\release\Xingkong_v1.0.0.apk"
set "WORK=D:\Agent\Xingkong\work"
cd /d "%WORK%"

echo [1/5] apktool 编译 smali -^> dex + 资源 ...
"%JAVA%" -Xmx4g -jar "%APKTOOL%" b "%SRC%" -o "%WORK%\xingkong-unsigned.apk" --use-aapt2
if errorlevel 1 goto fail

echo [2/5] zipalign 4 字节对齐 ...
"%BT%\zipalign.exe" -f 4 "%WORK%\xingkong-unsigned.apk" "%WORK%\xingkong-signed.apk"
if errorlevel 1 goto fail

echo [3/5] apksigner 签名 (debug.keystore) ...
"%BT%\apksigner.bat" sign --ks "%KEY%" --ks-pass pass:android --key-pass pass:android --ks-key-alias androiddebugkey "%WORK%\xingkong-signed.apk"
if errorlevel 1 goto fail

echo [4/5] 验证签名 ...
"%BT%\apksigner.bat" verify "%WORK%\xingkong-signed.apk"
if errorlevel 1 goto fail

echo [5/5] 输出到 release\ ...
copy /y "%WORK%\xingkong-signed.apk" "%OUT%" >nul

echo.
echo ============================================================
echo  打包完成: %OUT%
echo  快速核对:
"%BT%\aapt.exe" dump badging "%OUT%" 2>nul | findstr /i "package: application-label:"
echo ============================================================
endlocal
exit /b 0

:fail
echo.
echo ===== 打包失败，看上面报错 =====
endlocal
exit /b 1
