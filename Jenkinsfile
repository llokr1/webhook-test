pipeline {
    // Jenkins 호스트 머신에서 실행
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Build & Test') {
            steps {
                echo 'Starting Dockerized Gradle Build...'

                // sh 명령어를 사용하여 Docker 컨테이너 내부에서 빌드 실행
                // Docker 호스트 소켓을 공유하여 컨테이너 내에서 Docker 명령을 실행할 수 있습니다.
                sh """
                    # 1. 실행 권한 부여
                    chmod +x ./gradlew || true

                    # 2. eclipse-temurin:17-jdk 컨테이너를 사용하여 빌드 및 테스트 실행
                    # --rm: 종료 시 컨테이너 자동 삭제
                    # -v \${PWD}:/app: 현재 Jenkins Workspace를 컨테이너의 /app 디렉토리로 마운트
                    # -w /app: 컨테이너의 작업 디렉토리를 /app으로 설정
                    # -u \$(id -u):\$(id -g): 호스트의 사용자/그룹 ID로 컨테이너 실행 (권한 문제 방지)
                    docker run --rm \\
                        -u \$(id -u):\$(id -g) \\
                        -v \${PWD}:/app \\
                        -w /app \\
                        eclipse-temurin:17-jdk \\
                        sh -c './gradlew --no-daemon clean build'
                """
            }
        }
    }

    post {
        // 빌드 성공/실패 여부와 관계없이 실행
        always {
            echo 'Collecting Test Results and Artifacts...'
            // 테스트 결과 수집 (