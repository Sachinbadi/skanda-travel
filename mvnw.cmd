@ECHO OFF
SETLOCAL EnableDelayedExpansion

set MAVEN_PROJECTBASEDIR=%~dp0
if "%MAVEN_PROJECTBASEDIR:~-1%"=="\" set MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%

set WRAPPER_DIR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper
set WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar
set WRAPPER_PROPS=%WRAPPER_DIR%\maven-wrapper.properties

if not exist "%WRAPPER_PROPS%" (
  echo [ERROR] %WRAPPER_PROPS% not found
  exit /b 1
)

if not exist "%WRAPPER_JAR%" (
  for /f "tokens=1,2 delims==" %%A in (%WRAPPER_PROPS%) do (
    if "%%A"=="wrapperUrl" set WRAPPER_URL=%%B
  )

  if not defined WRAPPER_URL (
    echo [ERROR] wrapperUrl missing in maven-wrapper.properties
    exit /b 1
  )

  echo Downloading Maven wrapper jar...
  powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ProgressPreference='SilentlyContinue'; Invoke-WebRequest -UseBasicParsing '!WRAPPER_URL!' -OutFile '%WRAPPER_JAR%'"

  if errorlevel 1 (
    echo [ERROR] Could not download Maven wrapper jar
    exit /b 1
  )
)

where java >NUL 2>&1
if errorlevel 1 (
  echo [ERROR] Java not found in PATH. Install JDK 17+
  exit /b 1
)

set MAVEN_WRAPPER_MAIN=org.apache.maven.wrapper.MavenWrapperMain
java -classpath "%WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %MAVEN_WRAPPER_MAIN% %*

ENDLOCAL
