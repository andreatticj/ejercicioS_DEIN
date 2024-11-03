DROP SCHEMA IF EXISTS `veterinario` ;
CREATE SCHEMA IF NOT EXISTS `veterinario` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;

USE `veterinario` ;

DROP TABLE IF EXISTS `veterinario`.`Veterinario` ;

CREATE TABLE
    IF NOT EXISTS `veterinario`.`Veterinario` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `nombre` VARCHAR(250) NULL DEFAULT NULL,
        `especie` VARCHAR(250) NULL DEFAULT NULL,
        `raza` VARCHAR(250) NULL DEFAULT NULL,
        `sexo` VARCHAR(1) NULL DEFAULT NULL,
        `edad` INT NULL DEFAULT NULL,
        `peso` DECIMAL(20,2) NULL DEFAULT NULL,
        `observaciones` VARCHAR(250) NULL DEFAULT NULL,
        `fecha_primera_consulta` VARCHAR(10) NULL DEFAULT NULL,
	    `foto` blob NULL DEFAULT NULL,
        PRIMARY KEY (`id`)
    ) ENGINE = InnoDB DEFAULT CHARSET = latin1;
