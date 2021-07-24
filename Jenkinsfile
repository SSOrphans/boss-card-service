pipeline {
    agent any
    environment {
        serviceName = "card"
    }
    tools {
        git 'git'
        maven 'maven'
        terraform 'terraform'
    }
    stages {
        stage('Git Checkout') {
            steps {
                echo 'Git Checkout'
                git branch: 'demo/ecs-test', url: 'https://github.com/SSOrphans/boss-card-service.git'
                sh 'git submodule update --init'
            }
        }
        stage('Build') {
            steps {
                echo "Building $serviceName with maven"
                sh 'mvn clean package'
            }
        }
        stage('Docker Build') {
            environment {
                commitHash = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
            }
            steps {
                echo "Building and deploying docker image for $serviceName"
                withCredentials([string(credentialsId: 'awsRepo', variable: 'awsRepo')]) {
                    script {
                        docker.build('tf-$serviceName-repo:$commitHash')
                        docker.withRegistry('https://$awsRepo', 'ecr:us-east-2:aws-credentials') {
                            docker.image('tf-$serviceName-repo:$commitHash').push('$commitHash')
                        }
                    }
                }
            }    
        }
        stage('Terraform Init') {
            steps {
                dir('boss-terraform/deployment/ecs') {
                    sh 'terraform init -backend-config=./backends/ohio.hcl'
                }
            }
        }
        stage('Terraform Apply') {
            steps {
                dir('boss-terraform/deployment/ecs') {
                    sh 'terraform apply -var-file=./region-inputs/ohio.tfvars -auto-approve'
                }
            }
        }
    }
    post {
        cleanup {
            sh 'docker system prune -f'
            cleanWs()
        }
    }
}