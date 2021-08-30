pipeline {
  agent any

  environment {
      LOGIN = credentials('USER1')
//       PASS  = credentials('')
  }

  stages {
    stage("test") {
      steps {
        sh 'docker version'
        sh 'ls -al'
        sh 'cat main.go'
        sh 'cat build.groovy'
        echo "$LOGIN_USR"
        echo "$LOGIN_PSW"
        sh "ping docker"
      }
    }
  }
}