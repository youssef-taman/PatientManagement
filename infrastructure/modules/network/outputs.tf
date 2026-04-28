output "vpc_id" {
  value = module.vpc.vpc_id
}

output "public_subnets" {
  value = module.vpc.public_subnets
}

output "private_subnets" {
  value = module.vpc.private_subnets
}

output "alb_security_group_id" {
  value = aws_security_group.load_balancer.id
}

output "db_security_group_id" {
  value = aws_security_group.db.id
}

output "ecs_tasks_security_group_id" {
  value = aws_security_group.ecs_tasks.id
}

output "db_subnet_group_name" {
  value = aws_db_subnet_group.main.name
  
}