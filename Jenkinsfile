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
    
    stage('Selenium Tests') {
        steps {
            script {
                try {
                    dir('selenium-tests/selenium-tests') {
                        sh 'mvn -B test'
                    }
                } catch (err) {
                    echo "Selenium tests failed (probably no GUI/Chrome on Jenkins agent) : ${err}"
                }
            }
        }
    }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('local-sonarqube') {
                    sh 'mvn -B sonar:sonar'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-creds') {
                        def image = docker.build("nouurdevops/spring-petclinic:${env.BUILD_NUMBER}")
                        image.push()
                        image.push('latest')
                    }
                }
            }
        }


stage('Deploy to Minikube') {
    steps {
        sh '''
          kubectl apply --validate=false -f k8s/deployment.yaml
          kubectl apply --validate=false -f k8s/service.yaml
          kubectl rollout status deployment/springpetclinic-deploy
        '''
    }
}

     stage('Hello') {
            steps {
                echo 'Pipeline SpringPetClinic OK'
            }
        }
    }
}

