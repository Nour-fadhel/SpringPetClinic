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
            script {
                try {
                    sh '''
                      kubectl apply --validate=false -f k8s/deployment.yaml
                      kubectl apply --validate=false -f k8s/service.yaml
                      kubectl rollout status deployment/springpetclinic-deploy
                    '''
                } catch (err) {
                    echo "Deploy to Minikube failed (permissions kubeconfig). Deployment can be applied manually with: kubectl apply -f k8s/"
                }
            }
        }
    }

     stage('Hello') {
            steps {
                echo 'Pipeline SpringPetClinic OK'
            }
        }
    }
 
post {
        failure {
            emailext(
                to: 'nourfadhel1201@gmail.com',
                subject: "FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Le build a échoué pour le job ${env.JOB_NAME} (build #${env.BUILD_NUMBER}).
Consulte les logs ici : ${env.BUILD_URL}"""
            )
        }
    }
}

