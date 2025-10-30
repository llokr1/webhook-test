pipeline {
    // 1. 에이전트를 'any'로 변경 (Jenkins 기본 노드 사용)
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Build & Test') {
            steps {
                // 2. steps 내부에서 Docker 컨테이너를 실행하여 빌드 격리
                // 현재 작업 공간을 /app으로 마운트하고, 그 안에서 gradlew 명령 실행
                sh """
                    docker run --rm \\
                        -u \$(id -u):\$(id -g) \\
                        -v \${PWD}:/app \\
                        -w /app \\
                        eclipse-temurin:17-jdk \\
                        sh -c 'chmod +x ./gradlew && ./gradlew --no-daemon clean build'
                """
            }
        }
    }

    post {
        always {
            // 테스트 결과 수집 (JUnit) - 경로가 컨테이너 내부가 아닌 Jenkins Workspace 기준임을 유의
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
            archiveArtifacts artifacts: 'build/reports/tests/test/**', allowEmptyArchive: true
        }
        success {
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true, allowEmptyArchive: true
        }
    }
}