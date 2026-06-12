$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

"Launcher started $(Get-Date -Format o)" | Set-Content -Path "backend-current.log" -Encoding utf8
$sourceArgs = Join-Path $PSScriptRoot "target\backend-java-real.args"
$runtimeArgs = Join-Path $PSScriptRoot "target\backend-java-current.args"

$runtimeLines = Get-Content -LiteralPath $sourceArgs |
    Where-Object {
        $_ -notmatch '^--spring\.datasource\.' -and
        $_ -notmatch '^--spring\.jpa\.hibernate\.ddl-auto=' -and
        $_ -notmatch '^--spring\.jpa\.show-sql='
    }

$runtimeLines += @(
    "--spring.datasource.url=jdbc:h2:file:./target/iarts_local;MODE=MySQL;DATABASE_TO_UPPER=false",
    "--spring.datasource.username=sa",
    "--spring.datasource.password=",
    "--spring.datasource.driver-class-name=org.h2.Driver",
    "--spring.jpa.hibernate.ddl-auto=update",
    "--spring.jpa.show-sql=true"
)

$runtimeLines | Set-Content -LiteralPath $runtimeArgs -Encoding ascii

java "@$runtimeArgs" *>> "backend-current.log"
"Launcher finished $(Get-Date -Format o) exit=$LASTEXITCODE" | Add-Content -Path "backend-current.log" -Encoding utf8
