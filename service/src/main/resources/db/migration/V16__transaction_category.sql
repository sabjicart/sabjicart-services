CREATE TABLE SABJI_CART.TRANSACTION_CATEGORY
(
    ID INT8 NOT NULL,
    CATEGORY_NAME VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255) NOT NULL,
    ACTIVE BOOLEAN DEFAULT FALSE,
    CREATED_BY VARCHAR(255) NOT NULL,
    MODIFIED_BY VARCHAR(255) NOT NULL,
    TIME_CREATED DECIMAL(20) NOT NULL,
    TIME_UPDATED DECIMAL(20) NOT NULL,
    VERSION DECIMAL(20) NOT NULL,
    PROPERTIES JSON,
    CONSTRAINT TRANSACTION_CATEGORY_PKEY PRIMARY KEY (ID)
);

CREATE UNIQUE INDEX TRANSACTION_CATEGORY_IDX ON SABJI_CART.TRANSACTION_CATEGORY (CATEGORY_NAME);

INSERT INTO SABJI_CART.TRANSACTION_CATEGORY (ID, CATEGORY_NAME, DESCRIPTION, ACTIVE, CREATED_BY, MODIFIED_BY, TIME_CREATED, TIME_UPDATED, VERSION, PROPERTIES)
VALUES
(1002, 'PROC', 'PROCUREMENT TRANSACTION', TRUE, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000,EXTRACT(EPOCH FROM now()) * 1000,0,NULL),
(1003, 'TF-OT','OUTGOING TRANSFER OF FUNDS',TRUE, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000,EXTRACT(EPOCH FROM now()) * 1000,0,NULL),
(1004, 'TF-IN','INCOMING TRANSFER OF FUNDS',TRUE, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000,EXTRACT(EPOCH FROM now()) * 1000,0,NULL),
(1005, 'MISC','MISCELLANEOUS EXPENSE',TRUE, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000,EXTRACT(EPOCH FROM now()) * 1000,0,NULL),
(1006, 'DEBT','DEBT AMOUNT TO BE RECEIVED',TRUE, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000,EXTRACT(EPOCH FROM now()) * 1000,0,NULL),
(1007, 'SCRD','AMOUNT CREDITED VIA SALE',TRUE, 'SCRIPT', 'SCRIPT', EXTRACT(EPOCH FROM now()) * 1000,EXTRACT(EPOCH FROM now()) * 1000,0,NULL);