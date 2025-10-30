pipeline {
    agent any

    // 필요한 경우 ANSI 컬러 로그와 타임스탬프를 활성화합니다
    options {
        wrap([$class: 'AnsiColorBuildWrapper', colorMapName: 'xterm'])
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    // TODO: 에이전트에 JDK가 설치되어 있어야 합니다 (예: Java 17).
    // Jenkins의 Global Tool로 JDK를 관리하고 싶다면 tools 블록을 추가하고, 등록된 이름을 사용하세요.
    // 예)
    // tools {
    //     jdk 'YOUR_JDK_NAME'
    // }
    // 또는 stage 내에서 'tool' 스텝으로 JAVA_HOME을 설정한 뒤 'withEnv'로 PATH를 확장하세요.

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
    }
}

// 사용 가이드
// 1) 에이전트에 JDK(예: 17)가 설치되어 있는지 확인하세요.  // TODO
//    Jenkins Global Tool을 사용할 경우 tools { jdk '등록된이름' } 블록을 추가하세요.
// 2) Git 인증이 필요한 경우 Checkout 단계의 credentialsId, url을 알맞게 지정하세요.  // TODO
// 3) Windows 에이전트에서는 sh 단계를 bat 단계로 바꾸세요.  // TODO
// 4) 추가 환경변수가 필요하면 각 stage 내에서 withEnv 또는 withCredentials를 사용하세요.  // TODO

