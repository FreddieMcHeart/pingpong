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
        docker build -t test:me.1 .
        docker run --name test.me --rm --detach --publish 3000:3000
        curl -i -u test.me:test.me http://localhost:3000/auth
        curl -i -u test.me:test.me2 http://localhost:3000/auth
        docker stop $(docker -aq)
      }
    }
  }
}