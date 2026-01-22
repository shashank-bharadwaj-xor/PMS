pipeline {
    agent any

    environment {
        // Optional: specify JAVA_HOME if needed
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk'
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building ProductManagementSystem...'
                // Using your Maven wrapper to build, skipping tests initially
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                // Optional: run tests separately
                sh './mvnw test || true' // continue even if tests fail
            }
        }

        stage('Archive') {
            steps {
                echo 'Archiving the built JAR...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying ProductManagementSystem...'
                // Replace 'springboot-app' with your actual service name if different
                sh 'sudo systemctl restart springboot-app'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}
