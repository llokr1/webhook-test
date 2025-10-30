pipeline {
     agent any

     tools {
             // Jenkins Global Tool Configuration에 설정된 정확한 이름으로 변경
             jdk 'Java17' // <--- 이 부분을 Jenkins 설정의 이름으로 변경
         }

         stages {
         stage("Compile") {
             steps {
             sh "./gradlew compileJava"
             }
         }
         stage("Build") {
             steps {
             sh "./gradlew build"
             }
         }
         stage("Unit test") {
             steps {
             sh "./gradlew test"
              }
          }
      }
 }