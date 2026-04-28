resource "aws_ecs_task_definition" "services" {
  for_each = local.service_task_definitions

  family                   = each.key
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = each.value.cpu
  memory                   = each.value.memory

  execution_role_arn = var.execution_role_arn

  container_definitions = jsonencode([
    {
      name  = each.key
      image = each.value.image

      environment = [
        for key in sort(keys(each.value.environment)) : {
          name  = key
          value = each.value.environment[key]
        }
      ]

      secrets = contains(keys(each.value), "secrets") ? [
        for key in sort(keys(each.value.secrets)) : {
          name      = key
          valueFrom = each.value.secrets[key]
        }
      ] : []

      portMappings = [
        {
          containerPort = tonumber(each.value.container_port)
          hostPort      = tonumber(each.value.container_port)
        }
      ]
    }
  ])
}