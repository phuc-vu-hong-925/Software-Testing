@echo off
cd /d "%~dp0"
powershell -NoProfile -Command "$items = @('target/surefire-reports','target/screenshots','target/site/jacoco','target/test-environment.properties') | Where-Object { Test-Path $_ }; if (-not $items) { Write-Error 'No test results found. Run tests first.'; exit 1 }; Compress-Archive -Path $items -DestinationPath 'SauceDemo-results.zip' -Force"
pause
