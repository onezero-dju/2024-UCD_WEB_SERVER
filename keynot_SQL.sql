-- MySQL Script generated by MySQL Workbench
-- Mon Aug 26 23:34:18 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema keynote
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema keynote
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `keynote` DEFAULT CHARACTER SET utf8mb3 ;
USE `keynote` ;

-- -----------------------------------------------------
-- Table `keynote`.`organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `keynote`.`organization` (
  `organization_id` BIGINT NOT NULL AUTO_INCREMENT,
  `organization_name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`organization_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `keynote`.`channel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `keynote`.`channel` (
  `channel_id` BIGINT NOT NULL AUTO_INCREMENT,
  `Organization_id` BIGINT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`channel_id`),
  INDEX `fk_Channel_Group1_idx` (`Organization_id` ASC) VISIBLE,
  CONSTRAINT `fk_Channel_Group1`
    FOREIGN KEY (`Organization_id`)
    REFERENCES `keynote`.`organization` (`organization_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `keynote`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `keynote`.`user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `oauth_id` VARCHAR(255) NULL DEFAULT NULL,
  `role` VARCHAR(255) NOT NULL,
  `profile_image` VARCHAR(255) NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `keynote`.`user_organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `keynote`.`user_organization` (
  `user_id` BIGINT NOT NULL,
  `organization_id` BIGINT NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  `joined_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `organization_id`),
  INDEX `fk_User_has_Organization_Organization1_idx` (`organization_id` ASC) VISIBLE,
  INDEX `fk_User_has_Organization_User1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_User_has_Organization_Organization1`
    FOREIGN KEY (`organization_id`)
    REFERENCES `keynote`.`organization` (`organization_id`),
  CONSTRAINT `fk_User_has_Organization_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `keynote`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
