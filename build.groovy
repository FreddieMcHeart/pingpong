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
        sh 'docker network create test'
        sh 'docker build -t test:me.1 .'
        sh 'docker run --name test.me --network test --rm -d -p 0.0.0.0:3000:3000 test:me.1'
        sh 'docker run --name testing.you --network test --rm -d alpine tail -f > /dev/null'
        sh 'docker exec -it testing.you curl -i -u test.me:test.me2 http://test.me:3000/auth'
        sh 'docker exec -it testing.you curl -i -u test.me:test.me http://test.me:3000/auth'
        sh 'docker stop $(docker ps -aq)'
        ah 'docker network rm test'
      }
    }
  }
}