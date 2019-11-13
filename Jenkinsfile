pipeline {
  agent any
  stages {
    stage('Build Stage') {
      steps {
        echo "Build stage start"
        sh "sleep 5"
      }
    }
    stage("Testing stage") {
      steps {
        echo "Testing stage start"
        sh "sleep 5"
        stageMessage "Test run: 42, passed: 42, failed 0 https://www.sonarqube.org/"
      }
    }
    stage("Deploy stage") {
      steps {
        echo "Deploy stage start"
        sh "sleep 5"
      }
    }
  }
}
