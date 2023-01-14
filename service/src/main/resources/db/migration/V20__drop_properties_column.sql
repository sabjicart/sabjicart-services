DO
$$
DECLARE
rec record;
BEGIN
FOR rec IN
SELECT table_schema, table_name, column_name
FROM information_schema.columns
WHERE column_name = 'properties'
    LOOP
    EXECUTE format('ALTER TABLE %I.%I DROP %I;',
                   rec.table_schema, rec.table_name, rec.column_name);
END LOOP;
END;
$$