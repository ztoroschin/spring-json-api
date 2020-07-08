package ru.itmo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.itmo.api.model.Status;

@Getter
@AllArgsConstructor
public class StatisticsDTO {

    private final Integer id;
    private final Integer userId;
    private final Status status;
    private final Long timestamp;
}
