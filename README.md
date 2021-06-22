```
mvn sonar:sonar \
  -Dsonar.projectKey=ATM \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=0b59c9070a9e780ca1e1f16bc582ecb743b51e29 \
  -Dsonar.coverage.jacoco.xmlReportPaths=report1.xml,report2.xml
```