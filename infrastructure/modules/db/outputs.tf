output "db_username" {
  description = "root db username"
  value       = var.db_username
}
output "auth_db_endpoint" {
  description = "Address of the auth service MySQL instance."
  value       = aws_db_instance.auth.address
}

output "patient_db_endpoint" {
  description = "Address of the patient service MySQL instance."
  value       = aws_db_instance.patient.address
}

output "auth_db_name" {
  description = "Database name used by the auth service."
  value       = aws_db_instance.auth.db_name
}

output "patient_db_name" {
  description = "Database name used by the patient service."
  value       = aws_db_instance.patient.db_name
}


output "auth_db_password_secret_arn" {
  description = "Secrets Manager ARN for the auth DB password"
  value       = aws_db_instance.auth.master_user_secret[0].secret_arn
}

output "patient_db_password_secret_arn" {
  description = "Secrets Manager ARN for the patient DB password"
  value       = aws_db_instance.patient.master_user_secret[0].secret_arn
}