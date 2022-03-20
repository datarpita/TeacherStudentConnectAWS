provider "aws" {
  region = "${var.region}"
  shared_credentials_file = "C:/Users/Arpita Datta/.aws/credentials" 
}

#VPC
resource "aws_vpc" "teacher_student_dev_vpc" {
  cidr_block       = "${var.vpc-cidr}"
  instance_tenancy = "default"
  enable_dns_hostnames    = true
  tags = {
    Name = "teacher_student_dev_vpc"
  }
}

#Internet Gateway
resource "aws_internet_gateway" "teacher_student_dev_gw" {
  vpc_id = aws_vpc.teacher_student_dev_vpc.id
  tags = {
    Name = "teacher_student_dev_gw"
  }
}
#Public Subnet
resource "aws_subnet" "teacher_student_dev-public-subnet" {
  vpc_id     = "${aws_vpc.teacher_student_dev_vpc.id}"
  cidr_block = "${var.public-subnet-1-cidr}"
  availability_zone = "ap-south-1a"
  map_public_ip_on_launch = true
  tags = {
    Name = "teacher_student_dev-public-subnet"
  }
}

# Create Route Table and Add Public Route
# terraform aws create route table
resource "aws_route_table" "teacher_student_dev_public-rt-tbl" {
  vpc_id       = aws_vpc.teacher_student_dev_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.teacher_student_dev_gw.id
  }
  tags       = {
    Name     = "Public Route Table"
  }
}

# Associate Public Subnet 1 to "Public Route Table"
# terraform aws associate subnet with route table
resource "aws_route_table_association" "teacher_student_dev_public-subnet-1-rt-tbl-assc" {
  subnet_id           = aws_subnet.teacher_student_dev-public-subnet.id
  route_table_id      = aws_route_table.teacher_student_dev_public-rt-tbl.id
}

# Create EC2 instance 
# Connect to instance through SSH keys
# Copy jar file
# Run the jar file
resource "aws_security_group" "teacher_student_dev_sec_grp_ec2" {
  name        = "teacher_student_dev_sec_grp_ec2"
  description = "Security grp for EC2 instance" 
  vpc_id      = aws_vpc.teacher_student_dev_vpc.id
  # SSH access from anywhere
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  # HTTP access from the internet
  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  # outbound internet access
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_key_pair" "teacher_student_ec2_key_pair" {
  key_name   = "teacher_student_ec2_key_pair" # key pair name AWS
  public_key = "${file(var.public_key_path)}"
}

resource "aws_network_interface" "teacher_student_nw_int" {
   subnet_id   = aws_subnet.teacher_student_dev-public-subnet.id
   security_groups = [aws_security_group.teacher_student_dev_sec_grp_ec2.id]
   tags = {
     Name = "teacher_student_nw_int"
   }
 }

resource "aws_instance" "teacher_student_ec2_instance" {
  ami = data.aws_ami.ubuntu.id  
  instance_type = "t2.micro"
   network_interface {
     network_interface_id = aws_network_interface.teacher_student_nw_int.id
     device_index         = 0
  }
  key_name = "${aws_key_pair.teacher_student_ec2_key_pair.id}"
  #vpc_security_group_ids = ["${aws_security_group.teacher_student_dev_sec_grp_ec2.id}"]
  #subnet_id = "10.99.0.0/24"

  connection {  
    # The default username for our AMI
    user = "ubuntu"
    type = "ssh"
    private_key = "${file(var.private_key_path)}"
    # The connection will use the local SSH agent for authentication.
    host = self.public_ip
  }

  # install java, create dir
  provisioner "remote-exec" {
    inline = [
      "sudo apt-get -y update",
      "sudo apt-get -y install openjdk-8-jre-headless",
      "mkdir data",
      "cd data",      
    ]
  }

  # upload jar file
  provisioner "file" {
    source      = "TeacherStudentConnectUI.jar"
    destination = "/home/ubuntu/data/TeacherStudentConnectUI.jar"
  }

  # run jar
  provisioner "remote-exec" {
    inline = [
       "java -jar /home/ubuntu/data/TeacherStudentConnectUI.jar",
    ]
  }
}