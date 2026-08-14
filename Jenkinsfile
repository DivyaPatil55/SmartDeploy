pipeline {
  agent any
  environment {
    IMAGE = 'divyapatil5534/smartdeploy:latest'
    CONTAINER = 'smartdeploy-ci-test'
    EC2_HOST = credentials('ec2-host')
    EC2_KEY = credentials('ec2-ssh-key')
    EC2_USER = 'ubuntu'
  }
  stages {
    stage('Checkout'){steps{checkout scm}}
    stage('Maven Build'){steps{bat 'mvn clean package -DskipTests'}}
    stage('Docker Build'){steps{bat 'docker build -t %IMAGE% .'}}
    stage('Docker Test'){steps{bat '''docker rm -f %CONTAINER% 2>NUL || exit /b 0
docker run -d --name %CONTAINER% -p 8090:8081 %IMAGE%
powershell -Command "Start-Sleep -Seconds 8"
curl.exe -f http://localhost:8090/actuator/health'''}}
    stage('Docker Hub Push'){steps{withCredentials([usernamePassword(credentialsId:'dockerhub-creds',usernameVariable:'DOCKER_USER',passwordVariable:'DOCKER_PASSWORD')]){bat '''echo %DOCKER_PASSWORD% | docker login -u "%DOCKER_USER%" --password-stdin
docker push %IMAGE%
docker logout'''}}}
    stage('Deploy to EC2'){when{expression{return env.EC2_HOST?.trim() && env.EC2_KEY?.trim()}} steps{bat '''ssh -o StrictHostKeyChecking=no -i "%EC2_KEY%" %EC2_USER%@%EC2_HOST% "docker pull %IMAGE% && docker rm -f smartdeploy 2>/dev/null || true && docker run -d --restart unless-stopped --name smartdeploy -p 8081:8081 -v /var/run/docker.sock:/var/run/docker.sock %IMAGE%"'''}}
  }
  post {always{bat 'docker rm -f %CONTAINER% 2>NUL || exit /b 0'}}
}