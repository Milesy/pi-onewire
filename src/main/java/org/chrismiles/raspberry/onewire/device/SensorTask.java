package org.chrismiles.raspberry.onewire.device;

import org.apache.commons.io.IOUtils;
import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.chrismiles.raspberry.onewire.domain.OneWireSensorData;
import org.chrismiles.raspberry.onewire.repositories.OneWireSensorDataRepository;
import org.chrismiles.raspberry.onewire.repositories.OneWireSensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class SensorTask implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(SensorTask.class);

    private final OneWireSensor oneWireSensor;
    private OneWireSensorDataRepository oneWireSensorDataRepository;

    public SensorTask(final OneWireSensor sensor, final OneWireSensorDataRepository repository) {
        this.oneWireSensor = sensor;
        this.oneWireSensorDataRepository = repository;
    }

    @Override
    public void run() {
        try {
            OneWireSensorData oneWireSensorData = getOneWireSensorData();

            oneWireSensorDataRepository.save(oneWireSensorData);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private OneWireSensorData getOneWireSensorData() throws IOException {
        String data = IOUtils.toString(new FileInputStream(oneWireSensor.getFullPath()));
        OneWireSensorData oneWireSensorData = new OneWireSensorData();
        oneWireSensorData.setTimestamp(getTimestamp());
        oneWireSensorData.setData(data);
        oneWireSensorData.setOneWireSensor(oneWireSensor);

        return oneWireSensorData;
    }


    public OneWireSensor getOneWireSensor() {
        return oneWireSensor;
    }

    private Timestamp getTimestamp() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        return new Timestamp(date.getTime());
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("oneWireSensor", oneWireSensor.getSerial())
                .toString();
    }
}
