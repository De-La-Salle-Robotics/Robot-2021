pipeline {
    agent {
        docker {
            image wpilib/roborio-cross-ubuntu:2020-18.04
        }
    }
    stages {
        stage ('clean') {
            steps {
                sh './gradlew clean'
            }
        }
        stage ('build') {
            steps {
                sh './gradlew build'
            }
        }
    }
}