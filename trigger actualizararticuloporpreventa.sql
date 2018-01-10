DELIMITER$$
CREATE TRIGGER trgActualizarCantidadExistente BEFORE INSERT ON detallepreventa
FOR EACH ROW
BEGIN
SET @CantidadAntigua = (SELECT CantidadExistente FROM articulo WHERE CodigoArticulo = new.CodigoArticulo);
UPDATE articulo
SET CantidadExistente = @CantidadAntigua-new.Cantidad WHERE CodigoArticulo = new.CodigoArticulo;
END;
$$
DELIMITER;