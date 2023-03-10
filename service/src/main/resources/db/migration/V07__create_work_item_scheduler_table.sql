-- SCHEDULED JOB WORK ITEM CREATED BASED ON FREQUENCY

CREATE TABLE WORK_ITEM_SCHEDULER (
	ID INT8 NOT NULL,
	NAME VARCHAR(255) NOT NULL,
	LAST_RUN_START_TIME TIMESTAMP,
	LAST_RUN_END_TIME TIMESTAMP,
	LAST_SUCCESSFUL_RUN_TIME TIMESTAMP,
	STATUS VARCHAR(255) NOT NULL,
	DESCRIPTION VARCHAR(1024),
	ACTIVE BOOLEAN DEFAULT FALSE,
	CREATED_BY VARCHAR(255) NOT NULL,
	MODIFIED_BY VARCHAR(255) NOT NULL,
	TIME_CREATED DECIMAL(20) NOT NULL,
	TIME_UPDATED DECIMAL(20) NOT NULL,
	VERSION DECIMAL(20) NOT NULL,
	PROPERTIES JSON,
	CONSTRAINT WORK_ITEM_SCHEDULER_PKEY PRIMARY KEY (ID)
);

-- CAPTURING SCHEDULED JOB WORK ITEM RUN INFO

CREATE TABLE LOG_WORK_ITEM_SCHEDULER_RUN (
	ID INT8 NOT NULL,
	WORK_ITEM_SCHEDULER_ID INT8 NOT NULL,
	NAME VARCHAR(255) NOT NULL,
	RUN_TIME TIMESTAMP NOT NULL,
	STATUS VARCHAR(255) NOT NULL,
	ERROR_DESCRIPTION VARCHAR(1024),
	ACTIVE BOOLEAN DEFAULT FALSE,
	CREATED_BY VARCHAR(255) NOT NULL,
	MODIFIED_BY VARCHAR(255) NOT NULL,
	TIME_CREATED DECIMAL(20) NOT NULL,
	TIME_UPDATED DECIMAL(20) NOT NULL,
	VERSION DECIMAL(20) NOT NULL,
	PROPERTIES JSON,
	CONSTRAINT LOG_WORK_ITEM_SCHEDULER_RUN_PKEY PRIMARY KEY (ID)
);