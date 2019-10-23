library 'LEAD'
pipeline {
  agent any
  stages {
    stage('Build Stage') {
      steps {
        sh "git config -l | cat"

        sh "git config --global user.name 'test'"
        sh "git config --global user.name 'test@test.com'"
        sh "git config --global http.sslVerify false"

        notifyPipelineStart()
        notifyStageStart()
        echo "Build stage start"
        sh "sleep 5"
      }
      post {
        success {
          echo "Build stage success"
          notifyStageEnd()
        }
        failure {
          echo "Build stage fail"
          notifyStageEnd([result: "fail"])
        }
      }
    }
    stage("Testing stage") {
      steps {
        notifyStageStart()
        echo "Testing stage start"
        sh "sleep 5"
      }
      post {
        success {
          echo "Testing stage success"
          notifyStageEnd([status: "Test run: 42, passed: 42, failed 0 https://www.sonarqube.org/"])
        }
        failure {
          echo "Testing stage fail"
          notifyStageEnd([result: "fail"])
        }
      }
    }
    stage("Deploy stage") {
      steps {
        notifyStageStart()
        echo "Deploy stage start"
        sh "sleep 5"
      }
      post {
        success {
          echo "Deploy stage success"
          notifyStageEnd()
        }
        failure {
          echo "Deploy stage fail"
          notifyStageEnd([result: "fail"])
        }
      }
    }
  }
  post {
    success {
      echo "Pipeline Success"
      notifyPipelineEnd()
    }
    failure {
      echo "Pipeline Fail"
      notifyPipelineEnd([result: "fail"])
    }
  }
}
