INSERT INTO SABJI_CART.SUBSTATION
(ID, SUBSTATION_ID,SUBSTATION_NAME, ACTIVE, CREATED_BY, MODIFIED_BY, TIME_CREATED, TIME_UPDATED, VERSION, PROPERTIES)
VALUES
(10002, 'S101','Marathalli', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10003, 'S102','Gotigere', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10004, 'S103','Indiranagar', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10005, 'S104','Domlur', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL);

INSERT INTO SABJI_CART.SUBSTATION_CART
(ID, SUBSTATION_ID, CART_ID, CART_PLATE_NUMBER, CART_DRIVER_NAME, ACTIVE, CREATED_BY, MODIFIED_BY, TIME_CREATED, TIME_UPDATED, VERSION, PROPERTIES)
VALUES
(10002, 10002, 10002, 'KA-01-AA-01', 'Amar', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10003, 10003, 10003, 'KA-01-AA-02', 'Sita', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10004, 10004, 10004, 'KA-01-AA-03', 'Ram', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10005, 10005, 10005, 'KA-01-AA-04', 'John', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10006, 10002, 10006, 'KA-01-AA-05', 'Akhbar', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10007, 10002, 10007, 'KA-01-AA-06', 'Anthony', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10008, 10003, 10008, 'KA-01-AA-07', 'Geeta', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10009, 10004, 10009, 'KA-01-AA-08', 'Shyam', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10010, 10005, 10010, 'KA-01-AA-09', 'Johnny', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10011, 10005, 10011, 'KA-01-AA-10', 'Janardhan', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL);

INSERT INTO ADDRESS
(ID,SUBSTATION_ID, ADDRESS_LINE1, ADDRESS_LINE2, CITY, STATE, POSTCODE ,COUNTRY, ACTIVE, CREATED_BY, MODIFIED_BY, TIME_CREATED, TIME_UPDATED, VERSION, PROPERTIES)
VALUES
(10002, 10002, '#1, 1st Main, 1st Cross', '1st Block, Marathalli, Bangalore', 'Bangalore', 'Karnataka', 560034, 'India', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10003, 10003, '#2, 1st Main, 1st Cross', '1st Block, Gotigere, Bangalore', 'Bangalore', 'Karnataka', 560034, 'India', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10004, 10004, '#3, 1st Main, 1st Cross', '1st Block, Indiranagar, Bangalore', 'Bangalore', 'Karnataka', 560034, 'India', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL),
(10005, 10005, '#4, 1st Main, 1st Cross', '1st Block, Domlur, Bangalore', 'Bangalore', 'Karnataka', 560034, 'India', true, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000, EXTRACT(EPOCH FROM now()) * 1000, 0, NULL);