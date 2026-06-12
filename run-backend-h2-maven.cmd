y@echo off
cd /d "%~dp0"

"C:\Users\Joel.Mthombeni\.m2\wrapper\dists\apache-maven-3.9.16\0daed3be3ebd1c706f0e69e8b07c6b73f5cc4ea3dfce72a8d0ec2e849ca2ddb0\bin\mvn.cmd" spring-boot:run "-Dspring-boot.run.useTestClasspath=true" "-Dspring-boot.run.arguments=--spring.datasource.url=jdbc:h2:mem:iarts_local;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false --spring.datasource.username=sa --spring.datasource.password= --spring.datasource.driver-class-name=org.h2.Driver --spring.jpa.hibernate.ddl-auto=create-drop --spring.jpa.show-sql=false" >> "%~dp0backend.log" 2>> "%~dp0backend.err.log"
