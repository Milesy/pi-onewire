package org.chrismiles.raspberry.onewire.runner;

import org.chrismiles.raspberry.onewire.device.SensorLogger;
import org.chrismiles.raspberry.onewire.device.SensorRegistrar;
import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.chrismiles.raspberry.onewire.device.OneWireSensorReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OneWireRunnerTest {
    private OneWireRunner oneWireRunner;

    @Mock
    private OneWireSensorReader oneWireSensorReader;

    private List<OneWireSensor> data;

    @Mock
    private SensorRegistrar sensorRegistrar;

    @Mock
    private SensorLogger sensorLogger;

    @Before
    public void setUp() throws Exception {
        oneWireRunner = new OneWireRunner();
        oneWireRunner.setOneWireSensorReader(oneWireSensorReader);
        oneWireRunner.setSensorRegistrar(sensorRegistrar);
        oneWireRunner.setSensorLogger(sensorLogger);
        data = new ArrayList<>();
        data.add(new OneWireSensor());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRawDataIsReadAndSensorsRegisteredAndRawDataRecorded() throws Exception {
        givenTheDataReaderHasData();
        whenTheRunnerRuns();
        thenExpectTheDataReaderToHaveBeenCalled();
        thenExpectNewSensorsToBeRegistered();
        thenExpectRawDataToBeLogged();
    }

    private void thenExpectRawDataToBeLogged() {
        verify(sensorLogger).log();
    }

    private void thenExpectNewSensorsToBeRegistered() {
        verify(sensorRegistrar).registerNewSensors(data);
    }

    private void thenExpectTheDataReaderToHaveBeenCalled() {
        verify(oneWireSensorReader).read();
    }

    private void givenTheDataReaderHasData() {
        given(oneWireSensorReader.read()).willReturn(data);
    }

    private void whenTheRunnerRuns() throws Exception {
        oneWireRunner.run();
    }
}
