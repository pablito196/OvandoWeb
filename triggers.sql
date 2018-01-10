/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.0.21-community-nt : Database - ovandos
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `ovandos`;

/* Trigger structure for table `detallepreventa` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `tgrActualizarCantidadExistente` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `tgrActualizarCantidadExistente` BEFORE INSERT ON `detallepreventa` FOR EACH ROW BEGIN
SET @cantidadAntigua = (SELECT CantidadExistente FROM articulo where CodigoArticulo = new.CodigoArticulo);
update articulo
set CantidadExistente = @cantidadAntigua - new.Cantidad where CodigoArticulo = new.CodigoArticulo;
END */$$


DELIMITER ;

/* Trigger structure for table `entrada` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `trgActualizarCantidadPorEntrada` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `trgActualizarCantidadPorEntrada` BEFORE INSERT ON `entrada` FOR EACH ROW BEGIN
SET @CantidadAntigua = (SELECT CantidadExistente FROM articulo WHERE CodigoArticulo = new.CodigoArticulo);
UPDATE articulo
SET CantidadExistente = @CantidadAntigua+new.Cantidad WHERE CodigoArticulo = new.CodigoArticulo;
END */$$


DELIMITER ;

/* Trigger structure for table `salida` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `trgActualizarCantidadPorSalida` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `trgActualizarCantidadPorSalida` BEFORE INSERT ON `salida` FOR EACH ROW BEGIN
SET @CantidadAntigua = (SELECT CantidadExistente FROM articulo WHERE CodigoArticulo = new.CodigoArticulo);
UPDATE articulo
SET CantidadExistente = @CantidadAntigua-new.Cantidad WHERE CodigoArticulo = new.CodigoArticulo;
END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
