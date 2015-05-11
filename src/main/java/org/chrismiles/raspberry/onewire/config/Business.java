package org.chrismiles.raspberry.onewire.config;

import org.apache.commons.lang3.SystemUtils;
import org.chrismiles.raspberry.onewire.device.OneWireSensorReaderImpl;
import org.chrismiles.raspberry.onewire.device.OneWireSensorReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Business {
    @Bean
    public OneWireSensorReader getDeviceDiscoverer() {
        OneWireSensorReaderImpl oneWireSensorReader = new OneWireSensorReaderImpl();

        if(SystemUtils.IS_OS_LINUX) {
            oneWireSensorReader.setScanLocation("/sys/bus/w1/devices");
        }
        else {
            oneWireSensorReader.setScanLocation("C:\\pi\\sys\\bus\\w1\\devices");
        }

        return oneWireSensorReader;
    }
}
