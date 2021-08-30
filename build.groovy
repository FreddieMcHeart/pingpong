pipeline {
  agent any

  environment {
      LOGIN = credentials('USER1')
//       PASS  = credentials('')

  stages {
    stage("test") {
      steps {
        sh 'docker version'
        sh 'ls -al'
        sh 'cat main.go'
        echo $LOGIN
      }
    }
  }
}