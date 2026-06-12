$ErrorActionPreference = "Stop"

$log = Join-Path $PSScriptRoot "backend.log"
$dependencyClasspath = Get-Content -Raw (Join-Path $PSScriptRoot "target\test-classpath.txt")
$classpath = @(
  (Join-Path $PSScriptRoot "target\classes")
  (Join-Path $PSScriptRoot "target\test-classes")
  $dependencyClasspath.Trim()
) -join ";"

& java `
  "-cp" $classpath `
  "za.gov.dpw.iarts.IartsBackendApplication" `
  "--spring.datasource.url=jdbc:h2:mem:iarts_local;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false" `
  "--spring.datasource.username=sa" `
  "--spring.datasource.password=" `
  "--spring.datasource.driver-class-name=org.h2.Driver" `
  "--spring.jpa.hibernate.ddl-auto=create-drop" `
  "--spring.jpa.show-sql=false" `
  *>&1 | Tee-Object -FilePath $log
