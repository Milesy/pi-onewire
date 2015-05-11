package org.chrismiles.raspberry.onewire.device;

import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class OneWireSensorReaderImpl implements OneWireSensorReader {
    private static final Logger logger = LoggerFactory.getLogger(OneWireSensorReaderImpl.class);
    private String scanLocation;

    @Override
    public List<OneWireSensor> read() {
        File deviceRoot = new File(scanLocation);
        List<OneWireSensor> oneWireSensors = new ArrayList<>();

        for (File deviceDirectory : deviceRoot.listFiles()) {
            File[] files = deviceDirectory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.equals("w1_slave");
                }
            });

            if (files.length == 1) {
                OneWireSensor oneWireSensor = getSensor(deviceDirectory, files[0]);
                logger.info("Detected sensor: " + oneWireSensor.getFamily() + "-" + oneWireSensor.getSerial());
                oneWireSensors.add(oneWireSensor);
            }
        }

        return oneWireSensors;
    }

    // TODO - code coverage on serial? and other lines here.
    private OneWireSensor getSensor(File deviceDirectory, File file) {
        String familyAndSerial = deviceDirectory.getName();
        OneWireSensor.SensorFamily family = OneWireSensor.SensorFamily.fromCode(familyAndSerial.substring(0, familyAndSerial.indexOf("-")));
        String serial = familyAndSerial.substring(familyAndSerial.indexOf("-") + 1);
        OneWireSensor oneWireSensor = new OneWireSensor();
        oneWireSensor.setName(familyAndSerial);
        oneWireSensor.setFullPath(file.getAbsoluteFile().getAbsolutePath());
        oneWireSensor.setFamily(family);
        oneWireSensor.setSerial(serial);

        return oneWireSensor;
    }

    public void setScanLocation(String scanLocation) {
        this.scanLocation = scanLocation;
    }

    public String getScanLocation() {
        return scanLocation;
    }
}
