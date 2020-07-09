package ru.itmo.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.api.dto.StatisticsDTO;
import ru.itmo.api.service.StatisticsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StatisticsDTO> getStatistics(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "timestamp", required = false) String timestamp
    ) {
        List<StatisticsDTO> entries = new ArrayList<>();
        for (var entry : statisticsService.getStatistics(status, timestamp)) {
            entries.add(new StatisticsDTO(entry.getId(), entry.getUserId(), entry.getStatus(), entry.getTimestamp().getTime()));
        }
        return entries;
    }
}
