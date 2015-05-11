package org.chrismiles.raspberry.onewire.device;

import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OneWireSensorReaderImplTest {
    private OneWireSensorReaderImpl oneWireSensorReader;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private List<File> deviceDirectories;
    private List<OneWireSensor> data;

    @Before
    public void setUp() throws Exception {
        oneWireSensorReader = new OneWireSensorReaderImpl();
        deviceDirectories = new ArrayList<>();

        oneWireSensorReader.setScanLocation(temporaryFolder.getRoot().getPath());
    }

    private void givenADevice(String deviceName) throws IOException {
        File device = temporaryFolder.newFolder(deviceName);
        File slaveFile = new File(device.getPath() + "\\w1_slave");
        slaveFile.createNewFile();
        deviceDirectories.add(device);
    }

//    private void givenTheW1SlaveContents(File file, final int temp) throws IOException {
//        BufferedWriter output = new BufferedWriter(new FileWriter(file));
//        output.write("04 00 4b 46 7f ff 0c 10 dd : crc=dd YES");
//        output.write("04 00 4b 46 7f ff 0c 10 dd t=" + temp);
//        output.close();
//    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCanReadDevices() throws Exception {
        givenADevice("28-000000000001");

        whenTheDevicesAreDiscovered();

        thenThereShouldBeADeviceWithTheTempValue("28-000000000001");
    }

    private void thenThereShouldBeADeviceWithTheTempValue(String deviceName) {
        boolean found = false;

        for(OneWireSensor oneWireSensor : data) {
            if(oneWireSensor.getName().equals(deviceName)) {
                found = true;
                // make this seperate asserts for a single sensor for each test
                assertEquals(OneWireSensor.SensorFamily.DS18B20, oneWireSensor.getFamily());
                assertEquals(temporaryFolder.getRoot() + "\\" + oneWireSensor.getName() + "\\w1_slave",
                        oneWireSensor.getFullPath());
                break;
            }
        }

        if(!found) {
            fail("No device found [" + deviceName + "]");
        }
    }

    private void whenTheDevicesAreDiscovered() {
        data = oneWireSensorReader.read();
    }
}
