pipeline {
	agent {
        docker {
            image wpilib:roborio-cross-ubuntu
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