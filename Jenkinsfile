pipeline {
    agent {
        docker {
            image 'eclipse-temurin:17-jdk' // JDK 17이 포함된 공식 이미지
            args '-u root' // 필요 시 권한 문제 방지
        }
    }

    // 최소 옵션만 유지해 간단히 실행 가능하도록 합니다
    options {
        timestamps()
    }

    // 에이전트에 JDK(예: 17)가 설치되어 있어야 합니다.

    stages {
        stage('Build & Test') {
            steps {
                sh 'chmod +x ./gradlew || true'
                sh './gradlew --no-daemon clean build'
            }
        }
    }

    post {
        always {
            // 테스트 결과 수집 (JUnit)
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'

            // HTML 테스트 리포트(있다면) 보관
            archiveArtifacts artifacts: 'build/reports/tests/test/**', allowEmptyArchive: true
        }
        success {
            // 빌드 산출물(JAR) 보관
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true, allowEmptyArchive: true
        }
    }
}

// 사용 가이드
// 1) 에이전트에 JDK(예: 17)가 설치되어 있는지 확인하세요.  // TODO
// 2) 멀티브랜치/SCM 설정이 되어 있으면 checkout은 자동 처리됩니다.
// 3) Windows 에이전트에서는 sh 단계를 bat 단계로 바꾸세요.  // TODO

