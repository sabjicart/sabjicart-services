ALTER TABLE SABJI_CART.PROCUREMENT
ALTER COLUMN QUANTITY TYPE float8 USING QUANTITY::float8,
ALTER COLUMN COST_PRICE TYPE float8 USING COST_PRICE::float8,
ALTER COLUMN UNIT_SELLING_PRICE TYPE float8 USING UNIT_SELLING_PRICE::float8;