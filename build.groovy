pipeline {
  agent any

  stages {
    stage("test") {
      steps {
        sh 'docker version'
        sh 'ls -al'
        sh 'cat main.go'
      }
    }
  }
}