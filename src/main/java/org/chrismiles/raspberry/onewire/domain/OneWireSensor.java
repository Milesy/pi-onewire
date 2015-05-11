package org.chrismiles.raspberry.onewire.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "one_wire_sensor")
@SequenceGenerator(
        name = "one_wire_sensor_seq", sequenceName = "one_wire_sensor_seq", initialValue = 1, allocationSize = 1)
public class OneWireSensor {
    public enum SensorFamily {
        DS18S20("10", "1-Wire Parasite-Power Digital Thermometer."),
        DS18B20("28", "Programmable Resolution 1-Wire Digital Thermometer.");

        private final String code;
        private String description;

        SensorFamily(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static SensorFamily fromCode(String code) {
            assert (code != null);

            for (SensorFamily sensorFamily : SensorFamily.values()) {
                if (code.equalsIgnoreCase(sensorFamily.code)) {
                    return sensorFamily;
                }
            }

            return null;
        }
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "one_wire_sensor_seq")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "full_path")
    private String fullPath;

    @Column(name = "sensor_family")
    @Enumerated
    private SensorFamily family;

    @Column(name = "serial")
    private String serial;

    @Column(name = "poll_seconds")
    private Integer pollSeconds;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public SensorFamily getFamily() {
        return family;
    }

    public void setFamily(SensorFamily family) {
        this.family = family;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getPollSeconds() {
        return pollSeconds;
    }

    public void setPollSeconds(Integer pollSeconds) {
        this.pollSeconds = pollSeconds;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
