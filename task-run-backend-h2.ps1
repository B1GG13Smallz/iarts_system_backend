$ErrorActionPreference = "Stop"

$log = Join-Path $PSScriptRoot "task-launch.log"
try {
  "Launcher started $(Get-Date -Format o)" | Set-Content -Path $log
  Set-Location $PSScriptRoot
  & java "@$(Join-Path $PSScriptRoot "target\backend-java-real.args")" *>> $log
  "Launcher finished $(Get-Date -Format o) exit=$LASTEXITCODE" | Add-Content -Path $log
} catch {
  "Launcher failed $(Get-Date -Format o)" | Add-Content -Path $log
  $_ | Out-String | Add-Content -Path $log
  exit 1
}
