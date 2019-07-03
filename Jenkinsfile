library 'LEAD'
pipeline {
  agent any
  stages {
    stage('Build Stage') {
      steps {
        notifyPipelineStart([Jenkinsfile: 'Jenkinsfile-sample'])
        notifyStageStart()
        echo "Build stage start"
        sh "sleep 5"
      }
      post {
        success {
          echo "Build stage success"
          // sh "printenv | sort"
          notifyStageEnd()
        }
        failure {
          echo "Build stage fail"
          // sh "printenv | sort"
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
          // sh "printenv | sort"
          notifyStageEnd([status: "Test run: 42, passed: 42, failed 0 https://www.sonarqube.org/"])
        }
        failure {
          echo "Testing stage fail"
          // sh "printenv | sort"
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
          // sh "printenv | sort"
          notifyStageEnd()
        }
        failure {
          echo "Deploy stage fail"
          // sh "printenv | sort"
          notifyStageEnd([result: "fail"])
        }
      }
    }
  }
  post {
    success {
      echo "Pipeline Success"
      // sh "printenv | sort"
      notifyPipelineEnd()
    }
    failure {
      echo "Pipeline Fail"
      // sh "printenv | sort"
      notifyPipelineEnd([result: "fail"])
    }
  }
}
