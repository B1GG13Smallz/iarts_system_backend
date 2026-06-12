@echo off
setlocal
cd /d "%~dp0"

set "JAVA_EXE=C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot\bin\java.exe"
if not exist "%JAVA_EXE%" set "JAVA_EXE=java.exe"

"%JAVA_EXE%" @%~dp0target\backend-java.args >> "%~dp0backend.log" 2>> "%~dp0backend.err.log"
