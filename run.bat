@echo off
echo ===================================
echo    PUMABANK - Sistema Bancario
echo ===================================
echo.

echo Compilando proyecto...
call mvn clean compile

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La compilacion fallo
    pause
    exit /b 1
)

echo.
echo Ejecutando PumaBank...
echo.
call mvn exec:java

pause
