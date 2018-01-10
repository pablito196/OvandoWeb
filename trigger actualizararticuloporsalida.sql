DELIMITER$$
CREATE TRIGGER trgActualizarCantidadPorSalida BEFORE INSERT ON salida
FOR EACH ROW
BEGIN
SET @CantidadAntigua = (SELECT CantidadExistente FROM articulo WHERE CodigoArticulo = new.CodigoArticulo);
UPDATE articulo
SET CantidadExistente = @CantidadAntigua-new.Cantidad WHERE CodigoArticulo = new.CodigoArticulo;
END;
$$

DELIMITER ;