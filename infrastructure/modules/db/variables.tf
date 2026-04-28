variable "vpc_id" {
  description = "VPC ID where the databases will be deployed."
  type        = string
}

variable "private_subnets" {
  description = "Private subnet IDs used by the DB subnet group."
  type        = list(string)
}

variable "ecs_tasks_security_group_id" {
  description = "Security group ID of ECS tasks allowed to connect to MySQL."
  type        = string
}

variable "db_security_group_id" {
  description = "Security group ID of the database instances."
  type        = string
}

variable "db_username" {
  description = "Master username for both MySQL instances."
  type        = string
  default     = "root"
}

variable "auth_database_name" {
  description = "Database name for the auth service database instance."
  type        = string
  default     = "auth_db"
}

variable "patient_database_name" {
  description = "Database name for the patient service database instance."
  type        = string
  default     = "patient_db"
}

variable "db_subnet_group_name" {
  description = "Name of the DB subnet group."
  type        = string
}

