pipeline {
    agent any

    // 최소 옵션만 유지해 간단히 실행 가능하도록 합니다
    options {
        timestamps()
    }

    // 에이전트에 JDK(예: 17)가 설치되어 있어야 합니다.

    stages {
        stage('Setup JDK 17') {
            steps {
                sh '''
set -e
ARCH=$(uname -m)
OS=$(uname -s | tr '[:upper:]' '[:lower:]')
mkdir -p .jdk
if [ ! -d .jdk/temurin-17 ]; then
  cd .jdk
  case "$OS-$ARCH" in
    linux-aarch64) URL="https://github.com/adoptium/temurin17-binaries/releases/latest/download/OpenJDK17U-jdk_aarch64_linux_hotspot.tar.gz" ;;
    linux-x86_64) URL="https://github.com/adoptium/temurin17-binaries/releases/latest/download/OpenJDK17U-jdk_x64_linux_hotspot.tar.gz" ;;
    darwin-arm64) URL="https://github.com/adoptium/temurin17-binaries/releases/latest/download/OpenJDK17U-jdk_aarch64_mac_hotspot.tar.gz" ;;
    darwin-x86_64) URL="https://github.com/adoptium/temurin17-binaries/releases/latest/download/OpenJDK17U-jdk_x64_mac_hotspot.tar.gz" ;;
    *) echo "Unsupported platform: $OS-$ARCH"; exit 1 ;;
  esac
  echo "Downloading Temurin JDK 17 from $URL"
  curl -fsSL "$URL" -o jdk.tar.gz
  tar -xzf jdk.tar.gz
  rm jdk.tar.gz
  mv jdk-17* temurin-17
fi
'''
            }
        }

        stage('Build & Test') {
            steps {
                sh '''
set -e
export JAVA_HOME="$PWD/.jdk/temurin-17"
export PATH="$JAVA_HOME/bin:$PATH"
java -version
chmod +x ./gradlew || true
./gradlew --no-daemon -Dorg.gradle.java.installations.auto-download=false clean build
'''
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

