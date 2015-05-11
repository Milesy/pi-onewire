package org.chrismiles.raspberry.onewire.runner;

import org.chrismiles.raspberry.onewire.device.SensorLogger;
import org.chrismiles.raspberry.onewire.device.SensorRegistrar;
import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.chrismiles.raspberry.onewire.device.OneWireSensorReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OneWireRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(OneWireRunner.class);

    @Autowired
    private OneWireSensorReader oneWireSensorReader;

    @Autowired
    private SensorRegistrar sensorRegistrar;

    @Autowired
    private SensorLogger sensorLogger;

    public void setOneWireSensorReader(OneWireSensorReader oneWireSensorReader) {
        this.oneWireSensorReader = oneWireSensorReader;
    }

    public void setSensorRegistrar(SensorRegistrar sensorRegistrar) {
        this.sensorRegistrar = sensorRegistrar;
    }

    public void setSensorLogger(SensorLogger sensorLogger) {
        this.sensorLogger = sensorLogger;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("One wire runner started.");
        List<OneWireSensor> data = oneWireSensorReader.read();
        sensorRegistrar.registerNewSensors(data);
        sensorLogger.log();
    }
}
