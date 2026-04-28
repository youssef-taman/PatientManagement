output "service_name" {
	value       = aws_ecs_service.services["api-gateway"].name
	description = "Name of the primary ECS service."
}

