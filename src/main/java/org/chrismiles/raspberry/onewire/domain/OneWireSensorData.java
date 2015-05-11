package org.chrismiles.raspberry.onewire.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "one_wire_sensor_data")
@SequenceGenerator(
        name = "one_wire_sensor_data_seq", sequenceName = "one_wire_sensor_data_seq", initialValue = 1, allocationSize = 1)
public class OneWireSensorData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "one_wire_sensor_data_seq")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "one_wire_id", nullable = false)
    private OneWireSensor oneWireSensor;

    @Column(name = "data")
    private String data;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OneWireSensor getOneWireSensor() {
        return oneWireSensor;
    }

    public void setOneWireSensor(OneWireSensor oneWireSensor) {
        this.oneWireSensor = oneWireSensor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
