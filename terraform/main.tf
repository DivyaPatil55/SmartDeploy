data "aws_vpc" "default" {default=true}
data "aws_subnets" "default" {filter {name="vpc-id" values=[data.aws_vpc.default.id]}}
resource "aws_security_group" "smartdeploy" {name="smartdeploy-sg" description="SmartDeploy" vpc_id=data.aws_vpc.default.id
 ingress {from_port=22 to_port=22 protocol="tcp" cidr_blocks=[var.allowed_ssh_cidr]}
 ingress {from_port=8081 to_port=8081 protocol="tcp" cidr_blocks=[var.allowed_app_cidr]}
 egress {from_port=0 to_port=0 protocol="-1" cidr_blocks=["0.0.0.0/0"]}
 tags={Project="SmartDeploy"}}
resource "aws_instance" "smartdeploy" {ami=var.ami_id instance_type=var.instance_type subnet_id=data.aws_subnets.default.ids[0] key_name=var.key_name vpc_security_group_ids=[aws_security_group.smartdeploy.id] associate_public_ip_address=true tags={Name="SmartDeploy-EC2",Project="SmartDeploy"}}
