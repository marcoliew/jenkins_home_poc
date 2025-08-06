pipeline {
    agent any  // Runs on master since we removed agents
    
    options {
        timeout(time: 1, unit: 'HOURS')
        disableConcurrentBuilds()
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm  // Automatically checks out your source code
            }
        }
        
        stage('Build') {
            steps {
                script {
                    // Example for Java/Maven:
                    sh 'mvn clean package'
                    
                    // For Docker:
                    // sh 'docker build -t myapp .'
                }
            }
        }
        
        stage('Test') {
            steps {
                // Example test commands
                sh 'mvn test'
                junit '**/target/surefire-reports/*.xml'  // Archive test results
            }
        }
        
        stage('Deploy') {
            when {
                branch 'main'  // Only deploy from main branch
            }
            steps {
                // Example deployment steps
                sh 'kubectl apply -f k8s/'
                // or
                // sh 'docker push myrepo/myapp:${GIT_COMMIT}'
            }
        }
    }
    
    post {
        always {
            cleanWs()  // Clean workspace after build
            emailext (
                subject: "Build ${currentBuild.currentResult}: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """Check console output at ${BUILD_URL}""",
                to: 'team@example.com'
            )
        }
        success {
            slackSend(color: 'good', message: "Build Successful: ${env.JOB_NAME} #${env.BUILD_NUMBER}")
        }
        failure {
            slackSend(color: 'danger', message: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}")
        }
    }
}