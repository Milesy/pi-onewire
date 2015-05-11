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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SensorRegistrarImplTest {
    private SensorRegistrarImpl sensorRegistrar;
    @Mock
    private OneWireSensorRepository oneWireSensorRepository;
    private List<OneWireSensor> oneWireSensors;
    private OneWireSensor detectedSensor;
    private OneWireSensor repositorySensor;
    private OneWireSensor registeredSensor;

    @Before
    public void setUp() throws Exception {
        sensorRegistrar = new SensorRegistrarImpl();
        sensorRegistrar.setOneWireSensorRepository(oneWireSensorRepository);
        oneWireSensors = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testExistingSensorsShouldNotBeRegistered() throws Exception {
        givenThereIsADetectedSensor("28-00000001");
        givenTheSensorIsAlreadyRegistered("00000001");
        whenTheSensorsAreRegistered();
        thenTheSensorShouldNotBeRegistered();
    }

    private void thenTheSensorShouldNotBeRegistered() {
        verify(oneWireSensorRepository, never()).save(detectedSensor);
    }

    private void givenTheSensorIsAlreadyRegistered(String serial) {
        repositorySensor = new OneWireSensor();
        given(oneWireSensorRepository.findOneBySerial(serial)).willReturn(repositorySensor);
    }

    private void whenTheSensorsAreRegistered() {
        sensorRegistrar.registerNewSensors(oneWireSensors);
    }

    private void givenThereIsADetectedSensor(String sensorFamilyAndSerial) {
        String[] split = sensorFamilyAndSerial.split("-");
        detectedSensor = new OneWireSensor();
        detectedSensor.setFamily(OneWireSensor.SensorFamily.fromCode(split[0]));
        detectedSensor.setSerial(split[1]);
        oneWireSensors.add(detectedSensor);
    }

    @Test
    public void testNewSensorsShouldBeRegistered() throws Exception {
        // name should be like "emalias room" - this should be family-serial
        givenThereIsADetectedSensor("28-00000001");
        givenTheSensorIsAlreadyRegistered("28-00000002");
        whenTheSensorsAreRegistered();
        thenTheSensorShouldBeRegistered();

        thenTheRegisteredSensorNameShouldLookLike("UNNAMED");
        thenTheRegisteredSensorDescriptionShouldLookLike("UNNAMED");
        thenTheRegisteredSensorFamilyShouldLookLike("28");
        thenTheRegisteredSensorSerialShouldLookLike("00000001");
        thenTheRegisteredSensorTimestampShouldBeSet();
        thenTheRegisteredSensorPollMinutesShouldLookLike(600);
    }

    private void thenTheRegisteredSensorNameShouldLookLike(String name) {
        assertEquals(name, registeredSensor.getName());
    }

    private void thenTheRegisteredSensorDescriptionShouldLookLike(String description) {
        assertEquals(description, registeredSensor.getDescription());
    }

    private void thenTheRegisteredSensorSerialShouldLookLike(String serial) {
        assertEquals(serial, registeredSensor.getSerial());
    }

    private void thenTheRegisteredSensorFamilyShouldLookLike(String family) {
        assertEquals(family, registeredSensor.getFamily().getCode());
    }

    private void thenTheRegisteredSensorTimestampShouldBeSet() {
        assertTrue(registeredSensor.getTimestamp() != null);
    }

    private void thenTheRegisteredSensorPollMinutesShouldLookLike(Integer pollMinutes) {
        assertEquals(pollMinutes, registeredSensor.getPollSeconds());
    }

    private void thenTheSensorShouldBeRegistered() {
        ArgumentCaptor<OneWireSensor> captor = ArgumentCaptor.forClass(OneWireSensor.class);
        verify(oneWireSensorRepository).save(captor.capture());
        registeredSensor = captor.getValue();
    }
}
