package org.chrismiles.raspberry.onewire.device;

import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.chrismiles.raspberry.onewire.repositories.OneWireSensorDataRepository;
import org.chrismiles.raspberry.onewire.repositories.OneWireSensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SensorLoggerImpl implements SensorLogger {
    private static final Logger logger = LoggerFactory.getLogger(SensorLoggerImpl.class);

    @Autowired
    private OneWireSensorRepository oneWireSensorRepository;

    @Autowired
    private OneWireSensorDataRepository oneWireSensorDataRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    public void setOneWireSensorRepository(OneWireSensorRepository oneWireSensorRepository) {
        this.oneWireSensorRepository = oneWireSensorRepository;
    }

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void log() {
        List<OneWireSensor> oneWireSensors = oneWireSensorRepository.findAll();

        for (OneWireSensor oneWireSensor : oneWireSensors) {
            taskScheduler.scheduleAtFixedRate(
                    new SensorTask(oneWireSensor, oneWireSensorDataRepository),
                    oneWireSensor.getPollSeconds() * 1000);
        }
    }
}
