package org.chrismiles.raspberry.onewire.device;

import org.chrismiles.raspberry.onewire.domain.OneWireSensor;

import java.util.List;

public interface SensorRegistrar {
    void registerNewSensors(List<OneWireSensor> oneWireSensors);
}
