# VPC Variables
variable "region" {
  default       = "ap-south-1"
  description   = "AWS Region"
  type          = string
}

variable "vpc-cidr" {
  default       = "10.99.0.0/18"
  description   = "VPC CIDR Block"
  type          = string
}

variable "public-subnet-1-cidr" {
  default       = "10.99.0.0/24"
  description   = "Public Subnet 1 CIDR Block"
  type          = string
}

variable private_key_path{
  description = "Path to the SSH private key to be used for authentication"
  default = "teacher_student_ec2_key_pair"
}

variable public_key_path{
  description = "Path to the SSH public key to be used for authentication"
  default = "teacher_student_ec2_key_pair.pub"
}