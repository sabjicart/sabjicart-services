ALTER TABLE SABJI_CART.CART_ITEM DROP COLUMN processing_date;
ALTER TABLE SABJI_CART.CART_ITEM ADD COLUMN processing_date DECIMAL(20) NOT NULL;