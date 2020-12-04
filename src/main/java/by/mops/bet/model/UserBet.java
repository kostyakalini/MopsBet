package by.mops.bet.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user_bets")
public class UserBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double value;
    private String status;
    private Long bet_id;
    private Long user_id;

    public UserBet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBet_id() {
        return bet_id;
    }

    public void setBet_id(Long bet_id) {
        this.bet_id = bet_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "UserBet{" +
                "id=" + id +
                ", value=" + value +
                ", status='" + status + '\'' +
                ", bet_id=" + bet_id +
                ", user_id=" + user_id +
                '}';
    }
}
