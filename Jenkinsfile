pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Nour-fadhel/SpringPetClinic.git', branch: 'master'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn -B clean test'
            }
        }

        stage('Hello') {
            steps {
                echo 'Pipeline SpringPetClinic OK'
            }
        }
    }
}

