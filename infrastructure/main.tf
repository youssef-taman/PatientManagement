module "vpc" {
  source = "./modules/network"
}
module "alb" {
  source                = "./modules/alb"
  vpc_id                = module.vpc.vpc_id
  public_subnets        = module.vpc.public_subnets
  alb_security_group_id = module.vpc.alb_security_group_id
}

module "cloudmap" {
  source             = "./modules/cloudmap"
  vpc_id             = module.vpc.vpc_id
} 

module "db" {
  source                      = "./modules/db"
  vpc_id                      = module.vpc.vpc_id
  private_subnets             = module.vpc.private_subnets
  ecs_tasks_security_group_id = module.vpc.ecs_tasks_security_group_id
  db_subnet_group_name        = module.vpc.db_subnet_group_name
  db_security_group_id          = module.vpc.db_security_group_id
}

module "secret_manager" {
  source = "./modules/secret_manager"
}


module "ecs" {
  source                      = "./modules/ecs"

  private_subnets             = module.vpc.private_subnets

  alb_target_group_arn        = module.alb.alb_target_group_arn
  alb_listener_arn            = module.alb.listener_arn

  execution_role_arn          = module.iam.execution_role_arn

  ecs_tasks_security_group_id = module.vpc.ecs_tasks_security_group_id

  db_secret_arn               = module.secret_manager.db_secret_arn
  jwt_secret_arn              = module.secret_manager.jwt_secret_arn

  namespace_id                = module.cloudmap.namespace_id
  namespace_name              = module.cloudmap.namespace_name

  db_username                   = module.db.db_username

  auth_db_endpoint              = module.db.auth_db_endpoint
  auth_db_name                  = module.db.auth_db_name

  patient_db_endpoint           = module.db.patient_db_endpoint
  patient_db_name               = module.db.patient_db_name
}

module "iam" {
  source = "./modules/iam"
}

