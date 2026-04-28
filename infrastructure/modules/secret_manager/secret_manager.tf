resource "aws_secretsmanager_secret" "db" {
  name = "ROOT_PASSWORD"
}
resource "aws_secretsmanager_secret" "jwt" {
  name = "JWT_SECRET"
}