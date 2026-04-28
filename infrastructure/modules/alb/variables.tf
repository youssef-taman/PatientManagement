variable "vpc_id" {
  description = "The ID of the VPC where the ALB will be created."
  type        = string
}
variable "public_subnets" {
  description = "A list of public subnet IDs for the ALB."
  type        = list(string)
}
variable "alb_security_group_id" {
  description = "The security group ID for the ALB."
  type        = string
}