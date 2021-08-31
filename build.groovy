pipeline {
  agent any

  environment {
      LOGIN = credentials('USER1')
  }

  stages {
    stage("test") {
      steps {
        sh 'docker version'
        sh 'ls -al'
        sh 'cat main.go'
        sh 'cat build.groovy'
        sh 'docker build -t test:me.1 .'
        sh 'docker run --name test.me --rm -d -p 0.0.0.0:3000:3000 test:me.1'
        sh 'curl -i -u test.me:test.me2 http://docker:3000/auth'
        sh 'curl -i -u test.me:test.me http://docker:3000/auth'
        sh 'docker stop $(docker ps -aq)'
      }
    }
  }
}