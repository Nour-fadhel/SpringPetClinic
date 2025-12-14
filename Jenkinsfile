pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/karoumbr/SpringPetClinic.git'
            }
        }

        stage('Hello') {
            steps {
                echo 'Pipeline SpringPetClinic OK'
            }
        }
    }
}
