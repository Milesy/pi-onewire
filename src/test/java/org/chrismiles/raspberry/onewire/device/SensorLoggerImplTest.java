package org.chrismiles.raspberry.onewire.device;

import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.chrismiles.raspberry.onewire.repositories.OneWireSensorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.TaskScheduler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SensorLoggerImplTest {
    private SensorLoggerImpl sensorLogger;

    @Mock
    private OneWireSensorRepository oneWireSensorRepository;

    private List<OneWireSensor> repositoryOneWireSensors;

    @Mock
    private TaskScheduler taskScheduler;

    private OneWireSensor oneWireSensor;

    @Before
    public void setUp() throws Exception {
        sensorLogger = new SensorLoggerImpl();
        sensorLogger.setOneWireSensorRepository(oneWireSensorRepository);
        sensorLogger.setTaskScheduler(taskScheduler);
        repositoryOneWireSensors = new ArrayList<>();
        given(oneWireSensorRepository.findAll()).willReturn(repositoryOneWireSensors);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLog() throws Exception {
        givenTheSensorWithTheSerial("0000001");
        givenTheSensorHasThePollingTime(1000);
        whenTheLoggerRuns();
        thenExpectThatAllRegisteredSensorsAreFetched();
        thenExpectATaskToBeExecutedForThisSensor("0000001");
    }

    private void givenTheSensorHasThePollingTime(int pollMinutes) {
        oneWireSensor.setPollSeconds(pollMinutes);
    }

    private void thenExpectATaskToBeExecutedForThisSensor(String serial) {
        ArgumentCaptor<SensorTask> argumentCaptor = ArgumentCaptor.forClass(SensorTask.class);
        verify(taskScheduler).scheduleAtFixedRate(argumentCaptor.capture(), eq((long) oneWireSensor.getPollSeconds() * 1000));
        SensorTask sensorTask = argumentCaptor.getValue();
        assertEquals(serial, sensorTask.getOneWireSensor().getSerial());
    }

    private void givenTheSensorWithTheSerial(String serial) {
        oneWireSensor = new OneWireSensor();
        oneWireSensor.setSerial(serial);
        repositoryOneWireSensors.add(oneWireSensor);
    }

    private void thenExpectThatAllRegisteredSensorsAreFetched() {
        verify(oneWireSensorRepository).findAll();
    }

    private void whenTheLoggerRuns() {
        sensorLogger.log();
    }
}
