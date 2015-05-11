package org.chrismiles.raspberry.onewire.device;

import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.chrismiles.raspberry.onewire.repositories.OneWireSensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class SensorRegistrarImpl implements SensorRegistrar {
    private static final Logger logger = LoggerFactory.getLogger(SensorRegistrarImpl.class);

    @Autowired
    private OneWireSensorRepository oneWireSensorRepository;

    public void setOneWireSensorRepository(OneWireSensorRepository oneWireSensorRepository) {
        this.oneWireSensorRepository = oneWireSensorRepository;
    }

    @Override
    public void registerNewSensors(List<OneWireSensor> oneWireSensors) {
        logger.info("Registering " + oneWireSensors.size() + " sensors.");
        for (OneWireSensor oneWireSensor : oneWireSensors) {
            OneWireSensor registeredSensor = oneWireSensorRepository.findOneBySerial(oneWireSensor.getSerial());

            if (registeredSensor == null) {
                registerNewSensor(oneWireSensor);
            }
        }
    }

    private void registerNewSensor(OneWireSensor oneWireSensor) {
        oneWireSensor.setName("UNNAMED");
        oneWireSensor.setDescription("UNNAMED");
        oneWireSensor.setTimestamp(getTimestamp());
        oneWireSensor.setPollSeconds(600);

        logger.info("Registering new [" + oneWireSensor.getFamily() + "] " +
                "sensor with serial [" + oneWireSensor.getSerial() + "].");
        oneWireSensorRepository.save(oneWireSensor);
    }

    private Timestamp getTimestamp() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        return new Timestamp(date.getTime());
    }
}
