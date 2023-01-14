ALTER TABLE SABJI_CART.cart_item
ALTER COLUMN quantity_load TYPE float8 USING quantity_load::float8,
ALTER COLUMN quantity_unload TYPE float8 USING quantity_unload::float8,
ALTER COLUMN quantity_sale TYPE float8 USING quantity_sale::float8;