pipeline {
    agent any
    environment {
        serviceName = "ssor-card"
    }
    tools {
        git 'git'
        maven 'maven'
    }
    stages {
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
                        docker.build('$serviceName-repo:$commitHash')
                        docker.withRegistry('https://$awsRepo', 'ecr:us-east-2:aws-credentials') {
                            docker.image('$serviceName-repo:$commitHash').push('$commitHash')
                        }
                    }
                }
            }    
        }
        stage('Deploy') {
            environment {
                commitHash = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
            }
            steps {
                echo 'Deploying cloudformation..'
                withCredentials([string(credentialsId: 'awsAccountId', variable: 'awsAccountId'), string(credentialsId: 'awsRepo', variable: 'awsRepo')]) {
                    sh 'aws cloudformation deploy --stack-name $serviceName-stack --template-file ./ecs.yaml '+
                    '--parameter-overrides ApplicationName=$serviceName ApplicationEnvironment=dev '+
                    'ECRRepositoryUri=$awsRepo/$serviceName:$commitHash '+
                    'ExecutionRoleArn=arn:aws:iam::$awsAccountId:role/ecsTaskExecutionRole '+
                    'TargetGroupArn=arn:aws:elasticloadbalancing:us-east-2:$awsAccountId:targetgroup/default/a1d737973d78e824 '+
                    '--role-arn arn:aws:iam::$awsAccountId:role/awsCloudFormationRole '+
                    '--capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM --region us-east-2'
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