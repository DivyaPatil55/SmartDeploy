variable "aws_region" {type=string default="ap-south-1"}
variable "ami_id" {type=string description="Ubuntu 24.04 AMI ID"}
variable "instance_type" {type=string default="t2.micro"}
variable "key_name" {type=string description="Existing EC2 key pair name"}
variable "allowed_ssh_cidr" {type=string description="Your public IP/CIDR, e.g. 1.2.3.4/32"}
variable "allowed_app_cidr" {type=string default="0.0.0.0/0"}
