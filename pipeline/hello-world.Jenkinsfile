pipeline {
    agent { label 'master' }  // Forces execution on master
    stages {
        stage('Hello') {
            steps {
                script {
                    echo "Running on node: ${env.NODE_NAME}"
                    echo "Is this the master? ${env.NODE_NAME == 'master' ? 'YES' : 'NO'}"
                    sh 'hostname'  // Prints the hostname of the agent/master
                }
            }
        }
    }
}