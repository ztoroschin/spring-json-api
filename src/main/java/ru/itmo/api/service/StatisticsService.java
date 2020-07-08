package ru.itmo.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.api.exception.BadRequestException;
import ru.itmo.api.model.StatisticsEntry;
import ru.itmo.api.model.Status;
import ru.itmo.api.repository.StatisticsRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public List<StatisticsEntry> getStatistics(String status, String timestamp) {
        Status parsedStatus = null;
        Timestamp parsedTimestamp = null;
        if (status != null) {
            try {
                parsedStatus = Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException(e.getMessage());
            }
        }
        if (timestamp != null) {
            try {
                parsedTimestamp = new java.sql.Timestamp(Long.parseLong(timestamp));
            } catch (NumberFormatException e) {
                throw new BadRequestException(e.getMessage());
            }
        }

        if (parsedStatus != null && parsedTimestamp != null) {
            return getStatistics(parsedStatus, parsedTimestamp);
        }
        if (parsedStatus != null) {
            return getStatistics(parsedStatus);
        }
        if (parsedTimestamp != null) {
            return getStatistics(parsedTimestamp);
        }
        throw new BadRequestException("Can not parse arguments");
    }

    private List<StatisticsEntry> getStatistics(Status status) {
        return statisticsRepository.getByStatus(status);
    }

    private List<StatisticsEntry> getStatistics(Timestamp timestamp) {
        return statisticsRepository.getByTimestampAfter(timestamp);
    }

    private List<StatisticsEntry> getStatistics(Status status, Timestamp timestamp) {
        return statisticsRepository.getByStatusAndTimestampAfter(status, timestamp);
    }
}
