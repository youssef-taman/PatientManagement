resource "aws_db_instance" "auth" {
	identifier                   = "patient-management-auth-db"
	engine                       = "mysql"
	engine_version               = "8.0"
	instance_class               = "db.t3.micro"
	allocated_storage            = 20
	db_name                      = var.auth_database_name
	username                     = var.db_username
	manage_master_user_password  = true 
	db_subnet_group_name         = var.db_subnet_group_name
	vpc_security_group_ids       = [var.db_security_group_id]
	publicly_accessible          = false
	multi_az                     = false
	skip_final_snapshot          = true
	deletion_protection          = false
}

resource "aws_db_instance" "patient" {
	identifier                   = "patient-management-patient-db"
	engine                       = "mysql"
	engine_version               = "8.0"
	instance_class               = "db.t3.micro"
	allocated_storage            = 20
	db_name                      = var.patient_database_name
	username                     = var.db_username
    manage_master_user_password  = true
	db_subnet_group_name         = var.db_subnet_group_name
	vpc_security_group_ids       = [var.db_security_group_id]
	publicly_accessible          = false
	multi_az                     = false
	skip_final_snapshot          = true
	deletion_protection          = false
}

