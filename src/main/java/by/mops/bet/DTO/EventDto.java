package by.mops.bet.DTO;

import by.mops.bet.model.Bet;

import java.util.ArrayList;
import java.util.List;

public class EventDto {
    private Long id;
    private String team1;
    private String team2;
    private String status;
    private String result;
    private Integer total;
    private List<Bet> bets = new ArrayList<>();

    public EventDto(Long id, String team1, String team2, String status, String result, Integer total) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.status = status;
        this.result = result;
        this.total = total;
    }

    @Override
    public String toString() {
        return "EventDto{" +
                "id=" + id +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", status='" + status + '\'' +
                ", result='" + result + '\'' +
                ", total=" + total +
                ", bets=" + bets +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
}
