pipeline {
    agent any

    environment {
        IMAGE_NAME   = 'java-kind-demo'
        IMAGE_TAG    = "${BUILD_NUMBER}"
        KIND_CLUSTER = 'kind'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn -B clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -t ${IMAGE_NAME}:latest .'
            }
        }

        stage('Load Image into KIND') {
            steps {
                sh 'kind load docker-image ${IMAGE_NAME}:${IMAGE_TAG} --name ${KIND_CLUSTER}'
            }
        }

        stage('Deploy to KIND') {
            steps {
                sh '''
                    sed -i "s|image: .*|image: ${IMAGE_NAME}:${IMAGE_TAG}|" deployment.yaml
                    kubectl apply -f deployment.yaml
                    kubectl apply -f service.yaml
                    kubectl rollout status deployment/java-kind-demo --timeout=120s
                '''
            }
        }
    }

    post {
        success {
            echo 'Deployment succeeded.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
