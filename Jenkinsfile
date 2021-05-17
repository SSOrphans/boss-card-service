node{
    stage('Checkout'){
        checkout scm
    }
    stage('Build'){
        withMaven{
            ssh 'mvn clean package'
        }
    }
    stage('Deploy'){
        echo 'Deploying...'
    }
}