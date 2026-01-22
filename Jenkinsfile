pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk'
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        HOME = '/var/snap/jenkins/common' // Snap Jenkins home
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning from GitHub...'
                git branch: 'main', url: 'https://github.com/shashank-bharadwaj-xor/PMS.git'
            }
        }

        stage('Prepare Maven Wrapper') {
            steps {
                echo 'Fixing Maven wrapper permissions...'
                sh 'chmod +x mvnw'
            }
        }

        stage('Build') {
            steps {
                echo 'Building ProductManagementSystem (skip tests)...'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests (optional, continue on failure)...'
                sh './mvnw test || true'
            }
        }

        stage('Archive') {
            steps {
                echo 'Archiving built JAR...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying ProductManagementSystem...'
                sh 'sudo systemctl restart springboot-app || echo "Deployment skipped or failed"'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed! Check Jenkins logs.'
        }
    }
}
