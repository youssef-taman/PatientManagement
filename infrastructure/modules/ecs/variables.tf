
variable "private_subnets" {
  description = "A list of private subnet IDs for the ECS cluster."
  type        = list(string)
}
variable "alb_target_group_arn" {
  description = "The ARN of the ALB target group."
  type        = string

}
variable "alb_listener_arn" {
  description = "The ARN of the ALB listener."
  type        = string
}

variable "execution_role_arn" {
  description = "The IAM role ARN used by ECS task execution."
  type        = string
}

variable "ecs_tasks_security_group_id" {
  description = "The security group ID attached to ECS tasks."
  type        = string
}

variable "db_secret_arn" {
  description = "The ARN of the database secret in AWS Secrets Manager."
  type        = string
  
}
variable "jwt_secret_arn" {
  description = "The ARN of the JWT secret in AWS Secrets Manager."
  type        = string
  
}

variable "api_gateway_image" {
  description = "Container image for the API Gateway service."
  type        = string
  default     = "docker.io/yousseftaman/patientmanagement-api-gateway:v1"
}

variable "auth_service_image" {
  description = "Container image for the Auth service."
  type        = string
  default     = "docker.io/yousseftaman/patientmanagement-auth-service:v1"
}

variable "billing_service_image" {
  description = "Container image for the Billing service."
  type        = string
  default     = "docker.io/yousseftaman/patientmanagement-billing-service:v1"
}

variable "patient_service_image" {
  description = "Container image for the Patient service."
  type        = string
  default     = "docker.io/yousseftaman/patientmanagement-patient-service:v1"
}

variable "analytics_service_image" {
  description = "Container image for the Analytics service."
  type        = string
  default     = "docker.io/yousseftaman/patientmanagement-analytics-service:v1"
}

variable "kafka_image" {
  description = "Container image for the Kafka service."
  type        = string
  default     = "bitnamilegacy/kafka:latest"
}
variable "api_gateway_server_port" {
  description = "Server port for the API Gateway service."
  type        = string
  default     = "8080"
}

variable "auth_service_server_port" {
  description = "Server port for the Auth service."
  type        = string
  default     = "8084"
}

variable "billing_service_server_port" {
  description = "Server port for the Billing service."
  type        = string
  default     = "8082"
}

variable "patient_service_server_port" {
  description = "Server port for the Patient service."
  type        = string
  default     = "8081"
}

variable "analytics_service_server_port" {
  description = "Server port for the Analytics service."
  type        = string
  default     = "8083"
}

variable "kafka_server_port" {
  description = "Server port for the Kafka service."
  type        = string
  default     = "9092"
  
}

variable "ecs_cpu_size" {
  description = "CPU size for ECS tasks."
  type        = string
  default     = "256"
  
}
variable "ecs_memory_size" {
  description = "Memory size for ECS tasks."
  type        = string
  default     = "512" 
}

variable "namespace_id" {
  description = "The ID of the Cloud Map namespace."
  type        = string
}

variable "namespace_name" {
  description = "The name of the Cloud Map namespace."
  type        = string
}

variable "auth_db_endpoint" {
  description = "The endpoint of the auth database instance."
  type        = string
  
}

variable "auth_db_name" {
  description = "The name of the auth database."
  type        = string
  
}

variable "patient_db_endpoint" {
  description = "The endpoint of the patient database instance."
  type        = string

}

variable "patient_db_name" {
  description = "The name of the patient database."
  type        = string
}

variable "grpc_billing_port" {
  description = "gRPC server port for the Billing service."
  type        = string
  default     = "9090"
  
}

variable "grpc_patient_service_port" {
  description = "gRPC server port for the Patient service."
  type        = string
  default     = "9091"
  
}

variable "kafka_bootstrap_server_port" {
  description = "Bootstrap servers for Kafka."
  type        = string
  default     = "9092"
}


variable "db_username" {
  description = "db_username"
  type = "string"
}