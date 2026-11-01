param(
    [ValidateSet("all", "unit", "ui")] [string]$Mode = "all",
    [switch]$ShowBrowser,
    [string]$MavenPath = ""
)
$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
if (-not $MavenPath -and (Test-Path (Join-Path $PSScriptRoot "mvnw.cmd"))) { $MavenPath = Join-Path $PSScriptRoot "mvnw.cmd" }
if (-not $MavenPath) {
    $found = Get-Command mvn.cmd -ErrorAction SilentlyContinue
    if ($found) { $MavenPath = $found.Source }
}
if (-not $MavenPath) {
    $candidate = Get-ChildItem (Join-Path $env:USERPROFILE "Downloads") -Filter mvn.cmd -File -Recurse -ErrorAction SilentlyContinue |
        Where-Object { $_.Directory.Name -eq "bin" } | Select-Object -First 1
    if ($candidate) { $MavenPath = $candidate.FullName }
}
if (-not $MavenPath -or -not (Test-Path $MavenPath)) {
    throw 'Maven not found. Run: .\run-tests.ps1 -MavenPath "C:\path\to\maven\bin\mvn.cmd"'
}
Write-Host "Maven: $MavenPath"
$mavenArgs = @("-B", "clean", "verify")
if ($Mode -ne "all") { $mavenArgs += "-P$Mode" }
if ($ShowBrowser) { $mavenArgs += "-Dheadless=false" }
& $MavenPath @mavenArgs
$testExitCode = $LASTEXITCODE
if ($testExitCode -eq 0) {
    Write-Host 'SUCCESS - reports: target/surefire-reports; coverage: target/site/jacoco (all/unit only)'
} else { Write-Host 'FAILED - inspect target/surefire-reports and target/screenshots' }
exit $testExitCode
