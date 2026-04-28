resource "aws_security_group" "load_balancer" {
  name        = "load-balancer-sg"
  description = "Allow HTTP traffic to the load balancer"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description = "HTTP from anywhere"
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    description = "Allow all outbound traffic"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "load-balancer-sg"
  }
}

resource "aws_security_group" "ecs_tasks" {
  name        = "ecs-tasks-sg"
  description = "Allow traffic from ALB to ECS tasks"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description     = "Allow API Gateway traffic from ALB"
    from_port       = 8080
    to_port         = 8080
    protocol        = "tcp"
    security_groups = [aws_security_group.load_balancer.id]
  }

  egress {
    description = "Allow all outbound traffic"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "ecs-tasks-sg"
  }
}

resource "aws_security_group" "db" {
	name        = "patient-management-db-sg"
	description = "Allow ECS tasks to access MySQL databases"
	vpc_id      = module.vpc.vpc_id

	ingress {
		description     = "MySQL from ECS tasks"
		from_port       = 3306
		to_port         = 3306
		protocol        = "tcp"
		security_groups = [aws_security_group.ecs_tasks.id]
	}

	egress {
		from_port   = 0
		to_port     = 0
		protocol    = "-1"
		cidr_blocks = ["0.0.0.0/0"]
	}
}
