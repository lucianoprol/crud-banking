@echo off
set javaDefaultName=jdk-17.0.12
if not exist "C:\Trabajo\swdtools\%javaDefaultName%\bin\java.exe" (
  echo No existe java en C:\Trabajo\swdtools\%javaDefaultName%
  exit 2
)
set JAVA_HOME=C:\Trabajo\swdtools\%javaDefaultName%
set PATH="%JAVA_HOME\bin%;%PATH%"

if exist "C:\Trabajo\swdtools\apache-maven-3.9.9\runtime\bin\mvn.cmd" (
  set M2_HOME=C:\Trabajo\swdtools\apache-maven-3.9.9\runtime
) else (
  echo No existe maven en C:\Trabajo\swdtools\apache-maven-3.9.9\runtime
  exit 2
)

echo M2_HOME=%M2_HOME%
echo JAVA_HOME=%JAVA_HOME%

java -version

CD %~dp0
echo Ejecutando maven y dejo log en %~n0.log

call %M2_HOME%\bin\mvn clean install dependency:tree -DdownloadSources=true -Dclassifier=sources >> "%~dp0\%~n0.log" 2>&1
rem -Dmaven.test.skip=true
echo Ejecutado
