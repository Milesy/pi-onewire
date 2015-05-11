package org.chrismiles.raspberry.onewire.repositories;

import org.chrismiles.raspberry.onewire.domain.OneWireSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OneWireSensorRepository extends JpaRepository<OneWireSensor, Long>,
        JpaSpecificationExecutor<OneWireSensor> {
    OneWireSensor findOneByName(@Param(value = "name") String name);

    OneWireSensor findOneBySerial(@Param(value = "serial") String serial);
}
