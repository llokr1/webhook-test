pipeline {
    agent any

    // 필요한 경우 ANSI 컬러 로그와 타임스탬프를 활성화합니다
    options {
        ansiColor('xterm')
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    tools {
        // Jenkins에 사전 등록된 JDK 이름을 지정하세요
        // 예: 'JDK17' 또는 'temurin-17' 등
        jdk 'JDK17' // TODO: Jenkins에 등록된 JDK 이름으로 변경하세요
    }

    environment {
        // 필요 시 환경 변수를 설정하세요
        // EXAMPLE_TOKEN = credentials('CRED_ID') // TODO: 필요 시 Jenkins Credentials ID 입력
    }

    stages {
        stage('Checkout') {
            steps {
                // 기본적으로 멀티브랜치/SCM 설정 시 자동 checkout 됩니다.
                // 필요 시 아래를 커스터마이즈하세요.
                checkout scm
                // TODO: 특정 저장소/브랜치를 수동 지정하려면 아래 사용
                // git branch: 'main', credentialsId: 'YOUR_CRED_ID', url: 'https://your.git.repo.git'
            }
        }

        stage('Prepare') {
            steps {
                sh 'chmod +x ./gradlew || true' // 유닉스 기반 에이전트에서 gradlew 실행권한 보장
                // Windows 에이전트 사용 시 아래를 사용하세요
                // bat 'gradlew.bat --version'
            }
        }

        stage('Build') {
            steps {
                // Windows 에이전트 사용 시 bat로 교체하세요
                sh './gradlew --no-daemon clean build'
            }
        }

        stage('Test') {
            steps {
                // Windows 에이전트 사용 시 bat로 교체하세요
                sh './gradlew --no-daemon test'
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
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true, allowEmptyArchive: false
        }
        cleanup {
            // 필요 시 워크스페이스 정리 등
            // deleteDir()
        }
    }
}

// 사용 가이드
// 1) tools.jdk 의 값('JDK17')을 Jenkins에 등록된 JDK 이름으로 바꾸세요.  // TODO
// 2) Git 인증이 필요한 경우 Checkout 단계의 credentialsId, url을 알맞게 지정하세요.  // TODO
// 3) Windows 에이전트에서는 sh 단계를 bat 단계로 바꾸세요.  // TODO
// 4) 추가 환경변수가 필요하면 environment 블록을 편집하세요.  // TODO

