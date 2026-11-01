@echo off
cd /d "%~dp0"
call mvnw.cmd -B -Punit clean verify
set "TEST_EXIT=%ERRORLEVEL%"
echo.
echo Exit code: %TEST_EXIT%
pause
exit /b %TEST_EXIT%
