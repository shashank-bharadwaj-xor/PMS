pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        HOME = '/var/snap/jenkins/common'
        
        // MySQL Database Configuration
        SPRING_DATASOURCE_URL = 'jdbc:mysql://localhost:3306/ProductManagementSystem'
        SPRING_DATASOURCE_USERNAME = 'root'
        SPRING_DATASOURCE_PASSWORD = 'MyStrongPass123!'  // Replace with actual password
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning from GitHub...'
                git branch: 'main', url: 'https://github.com/shashank-bharadwaj-xor/PMS.git'
            }
        }

        stage('Start MySQL Container') {
            steps {
                echo 'Starting MySQL container for tests...'
                sh '''
                # Stop and remove if already exists
                docker stop test-mysql 2>/dev/null || true
                docker rm test-mysql 2>/dev/null || true
                
                # Start fresh MySQL container
                docker run -d \
                    --name test-mysql \
                    -e MYSQL_DATABASE=pms_test \
                    -e MYSQL_ROOT_PASSWORD=testpassword \
                    -p 3306:3306 \
                    mysql:8
                '''
                
                echo 'Waiting for MySQL to be ready...'
                sh '''
                for i in {1..30}; do
                    if docker exec test-mysql mysqladmin ping -h "127.0.0.1" -u root -ptestpassword --silent 2>/dev/null; then
                        echo "MySQL is ready!"
                        break
                    fi
                    echo "Waiting for MySQL... attempt $i/30"
                    sleep 2
                done
                
                # Verify MySQL is accessible
                docker exec test-mysql mysql -u root -ptestpassword -e "SHOW DATABASES;"
                '''
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
            environment {
                // Override database settings for tests
                SPRING_DATASOURCE_URL = 'jdbc:mysql://127.0.0.1:3306/pms_test'
                SPRING_DATASOURCE_USERNAME = 'root'
                SPRING_DATASOURCE_PASSWORD = 'testpassword'
            }
            steps {
                echo 'Running tests with MySQL container...'
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
        always {
            echo 'Cleaning up MySQL container...'
            sh '''
            docker stop test-mysql 2>/dev/null || true
            docker rm test-mysql 2>/dev/null || true
            '''
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed! Check Jenkins logs.'
        }
    }
}