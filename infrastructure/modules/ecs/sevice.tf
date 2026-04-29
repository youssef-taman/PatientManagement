resource "aws_ecs_service" "services" {
  for_each = local.service_task_definitions

  name            = "${each.key}-service"
  cluster         = aws_ecs_cluster.main.id
  task_definition = aws_ecs_task_definition.services[each.key].arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets          = var.private_subnets
    security_groups  = [var.ecs_tasks_security_group_id]
    assign_public_ip = false
  }

  dynamic "load_balancer" {
    for_each = each.key == "api-gateway" ? [1] : []
    content {
      target_group_arn = var.alb_target_group_arn
      container_name   = each.key
      container_port   = each.value.container_port
    }
  }

  dynamic "service_registries" {
    for_each = [aws_service_discovery_service.services[each.key].arn]
    content {
      registry_arn   = service_registries.value
      # container_name  = each.key
      # container_port  = each.value.container_port
    }
  }
}

