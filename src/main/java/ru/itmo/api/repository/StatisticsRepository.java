package ru.itmo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.api.model.StatisticsEntry;
import ru.itmo.api.model.Status;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<StatisticsEntry, Integer> {

    List<StatisticsEntry> getByStatus(Status status);

    List<StatisticsEntry> getByTimestampAfter(Timestamp timestamp);

    List<StatisticsEntry> getByStatusAndTimestampAfter(Status status, Timestamp timestamp);
}
