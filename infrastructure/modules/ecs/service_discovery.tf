resource "aws_service_discovery_service" "services" {
  for_each = local.service_task_definitions

  name = each.key

  dns_config {
    namespace_id = var.namespace_id
    routing_policy = "MULTIVALUE"

    dns_records {
      ttl  = 10
      type = "A"
    }
  }
}