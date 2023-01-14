CREATE TABLE ADDRESS (
                                               ID INT8 NOT NULL,
                                               SUBSTATION_ID INT8 NOT NULL,
                                               ADDRESS_LINE1 VARCHAR(255) NOT NULL,
                                               ADDRESS_LINE2 VARCHAR(255),
                                               CITY VARCHAR(255) NOT NULL,
                                               POSTCODE VARCHAR(255) NOT NULL,
                                               STATE VARCHAR(255),
                                               COUNTRY VARCHAR(255) NOT NULL,
                                               ACTIVE BOOLEAN DEFAULT FALSE,
                                               CREATED_BY VARCHAR(255) NOT NULL,
                                               MODIFIED_BY VARCHAR(255) NOT NULL,
                                               TIME_CREATED DECIMAL(20) NOT NULL,
                                               TIME_UPDATED DECIMAL(20) NOT NULL,
                                               VERSION DECIMAL(20) NOT NULL,
                                               PROPERTIES JSON,
                                               CONSTRAINT ADDRESS_PKEY PRIMARY KEY (ID)
);