package org.chrismiles.raspberry.onewire.repositories;

import org.chrismiles.raspberry.onewire.domain.OneWireSensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OneWireSensorDataRepository extends JpaRepository<OneWireSensorData, Long>,
        JpaSpecificationExecutor<OneWireSensorData> {
}
