# SmartDeploy – AWS Auto Deployment Platform

Fresh interview-focused project using GitHub, Jenkins, Maven, Docker, Docker Hub, Terraform, Ansible and AWS EC2. The dashboard also provides Docker service Start/Stop/Restart/Remove/Add operations.

## What is already created
- Spring Boot backend + Actuator health endpoint
- Modern dashboard UI
- Docker service-management REST APIs
- Dockerfile
- Jenkins CI/CD pipeline
- Terraform EC2 + Security Group infrastructure
- Ansible EC2 Docker configuration
- README and .gitignore

## You only need to do
1. Extract the ZIP.
2. Open it in VS Code.
3. Run the local build: `mvn clean package`.
4. Install/configure Jenkins, Docker Desktop, AWS CLI, Terraform and Ansible as instructed in the README.
5. Put your own Docker Hub username, access token, EC2 IP and SSH key into Jenkins Credentials.
6. Fill Terraform variables before `terraform apply`.

## Jenkins credentials
Create `dockerhub-creds` as Username with password; password must be a Docker Hub Access Token. Create `ec2-host` as Secret text and `ec2-ssh-key` as Secret file.

## Terraform
Copy `terraform/terraform.tfvars.example` to `terraform/terraform.tfvars`, set your Ubuntu AMI, existing EC2 key-pair name and your own public IP/32, then run `terraform init`, `terraform plan`, `terraform apply`. Never commit credentials or tfstate.

## Ansible
Edit `ansible/inventory.ini` with the EC2 public IP and run `ansible-playbook -i inventory.ini site.yml` from WSL/Linux.

## Local
`mvn spring-boot:run` then open `http://localhost:8081`. Health: `http://localhost:8081/actuator/health`.

## Production
Jenkins builds/tests the app, pushes `divyapatil5534/smartdeploy:latest`, then SSH deploys it to EC2. The EC2 container should mount `/var/run/docker.sock` so the dashboard can manage host containers. Docker socket access is highly privileged; use this only on a trusted server.
