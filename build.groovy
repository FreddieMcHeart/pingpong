pipeline {
  agent any

  environment {
      LOGIN_C = credentials('test.me-c')
      LOGIN_INC = credentials('test.me-inc')
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
        sh 'curl -i -u $LOGIN_C_USR:$LOGIN_C_PSW http://docker:3000/auth'
        sh 'curl -i -u $LOGIN_INC_USR:LOGIN_INC_PSW http://docker:3000/auth'
        sh 'docker stop $(docker ps -aq)'
      }
    }
  }
}