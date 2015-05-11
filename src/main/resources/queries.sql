SELECT position('t=' in data) FROM one_wire_sensor_data   ;

SELECT data FROM one_wire_sensor_data;

SELECT
  cast(substring(data from position('t=' in data) + 2) as int) / 1000::float as temp,
  cast(timestamp as date) as date
FROM one_wire_sensor_data
WHERE one_wire_id = 5;


SELECT * FROM one_wire_sensor;
SELECT * FROM one_wire_sensor_data;

DELETE FROM one_wire_sensor;
DELETE FROM one_wire_sensor_data;
