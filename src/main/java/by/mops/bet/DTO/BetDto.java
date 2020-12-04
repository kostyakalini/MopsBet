package by.mops.bet.DTO;

import by.mops.bet.model.Bet;

import java.util.ArrayList;
import java.util.List;

public class BetDto {
    private Long id;
    private Long event_id;
    private Long type_of_bet_id;
    private String value;
    private Double coefficient;

    public BetDto() {
    }

    public BetDto(Long id, Long event_id, Long type_of_bet_id, String value, Double coefficient) {
        this.id = id;
        this.event_id = event_id;
        this.type_of_bet_id = type_of_bet_id;
        this.value = value;
        this.coefficient = coefficient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Long getType_of_bet_id() {
        return type_of_bet_id;
    }

    public void setType_of_bet_id(Long type_of_bet_id) {
        this.type_of_bet_id = type_of_bet_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        return "BetDto{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", type_of_bet_id=" + type_of_bet_id +
                ", value='" + value + '\'' +
                ", coefficient=" + coefficient +
                '}';
    }
}
