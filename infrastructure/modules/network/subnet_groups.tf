resource "aws_db_subnet_group" "main" {
	name       = "patient-management-db-subnets"
	subnet_ids = module.vpc.private_subnets
}
