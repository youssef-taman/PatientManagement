resource "aws_secretsmanager_secret" "jwt" {
  name = "JWT_SECRET"
}
