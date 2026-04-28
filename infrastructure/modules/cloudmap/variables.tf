variable "name" {

    description = "Name of the Cloud Map (Service Discovery) namespace"
    type        = string
    default = "app.local"
}

variable "vpc_id" {
    description = "VPC ID to associate the private DNS namespace with"
    type        = string
}

