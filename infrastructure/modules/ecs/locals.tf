locals {
  service_task_definitions = {
    "api-gateway" = {
      image          = var.api_gateway_image
      container_port = var.api_gateway_server_port
      cpu            = var.ecs_cpu_size
      memory         = var.ecs_memory_size
      environment = {
        SERVER_PORT      = var.api_gateway_server_port
        AUTH_SERVICE_URL = "http://auth-service.${var.namespace_name}:${var.auth_service_server_port}"
      }
    }
    "auth-service" = {
      image          = var.auth_service_image
      container_port = var.auth_service_server_port
      cpu            = var.ecs_cpu_size
      memory         = var.ecs_memory_size
      environment = {
        SERVER_PORT                   = var.auth_service_server_port
        SPRING_DATASOURCE_URL         = "jdbc:mysql://${var.auth_db_endpoint}:3306/${var.auth_db_name}"
        SPRING_DATASOURCE_USERNAME    = var.db_username
        SPRING_JPA_HIBERNATE_DDL_AUTO = "create-drop"
      }
      secrets = {
        SPRING_DATASOURCE_PASSWORD = var.db_secret_arn
        JWT_SECRET                 = var.jwt_secret_arn
      }
    }
    "billing-service" = {
      image          = var.billing_service_image
      container_port = var.billing_service_server_port
      cpu            = var.ecs_cpu_size
      memory         = var.ecs_memory_size
      environment = {
        SERVER_PORT      = var.billing_service_server_port
        GRPC_SERVER_PORT = var.grpc_billing_port
      }
    }
    "patient-service" = {
      image          = var.patient_service_image
      container_port = var.patient_service_server_port
      cpu            = var.ecs_cpu_size 
      memory         = var.ecs_memory_size
      environment = {
        SPRING_DATASOURCE_URL                  = "jdbc:mysql://${var.patient_db_endpoint}:3306/${var.patient_db_name}"
        SPRING_DATASOURCE_USERNAME             = var.db_username
        SPRING_JPA_HIBERNATE_DDL_AUTO          = "update"
        SERVER_PORT                            = var.patient_service_server_port
        GRPC_SERVER_PORT                       = var.grpc_patient_service_port
        GRPC_BILLING_SERVICE_HOST              = "billing-service.${var.namespace_name}"
        GRPC_BILLING_SERVICE_PORT              = var.grpc_billing_port
        SPRING_KAFKA_BOOTSTRAP_SERVERS         = "kafka.${var.namespace_name}:${var.kafka_bootstrap_server_port}"
        SPRING_KAFKA_PRODUCER_KEY_SERIALIZER   = "org.apache.kafka.common.serialization.StringSerializer"
        SPRING_KAFKA_PRODUCER_VALUE_SERIALIZER = "org.apache.kafka.common.serialization.ByteArraySerializer"
      }
      secrets = {
        SPRING_DATASOURCE_PASSWORD = var.db_secret_arn
      }
    }
    "analytics-service" = {
      image          = var.analytics_service_image
      container_port = var.analytics_service_server_port
      cpu            = var.ecs_cpu_size
      memory         = var.ecs_memory_size
      environment = {
        SERVER_PORT                              = var.analytics_service_server_port
        SPRING_KAFKA_BOOTSTRAP_SERVERS           = "kafka.${var.namespace_name}:${var.kafka_bootstrap_server_port}"
        SPRING_KAFKA_CONSUMER_KEY_DESERIALIZER   = "org.apache.kafka.common.serialization.StringDeserializer"
        SPRING_KAFKA_CONSUMER_VALUE_DESERIALIZER = "org.apache.kafka.common.serialization.ByteArrayDeserializer"
      }
    }
    "kafka" = {
      image          = var.kafka_image
      container_port = var.kafka_server_port
      cpu            = var.ecs_cpu_size
      memory         = var.ecs_memory_size
      environment = {
        KAFKA_CFG_NODE_ID                        = "1"
        KAFKA_CFG_PROCESS_ROLES                  = "broker,controller"
        KAFKA_CFG_LISTENERS                      = "PLAINTEXT://:${var.kafka_server_port},CONTROLLER://:9093"
        KAFKA_CFG_ADVERTISED_LISTENERS           = "PLAINTEXT://kafka.${var.namespace_name}:${var.kafka_bootstrap_server_port}"
        KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP = "PLAINTEXT:PLAINTEXT,CONTROLLER:PLAINTEXT"
        KAFKA_CFG_CONTROLLER_LISTENER_NAMES      = "CONTROLLER"
        KAFKA_CFG_CONTROLLER_QUORUM_VOTERS       = "1@kafka.${var.namespace_name}:9093"
        KAFKA_KRAFT_CLUSTER_ID                   = "abcdefghijklmnopqrstuv"
        ALLOW_PLAINTEXT_LISTENER                 = "yes"
      }
    }
  }
}