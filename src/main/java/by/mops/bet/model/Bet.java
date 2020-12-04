package by.mops.bet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bytebuddy.asm.Advice;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@Entity
@Table(name = "bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double coefficient;
    private String value;
    private Long event_id;
    private Long type_of_bet_id;

    @OneToMany(targetEntity = UserBet.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "bet_id", referencedColumnName = "id")
    private List<UserBet> userBets = new ArrayList<>();

    //private Event event;
    /*@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "type_of_bet_id")
    private TypeOfBet typeOfBet;*/

    public Bet() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public List<UserBet> getUserBets() {
        return userBets;
    }

    public void setUserBets(List<UserBet> userBets) {
        this.userBets = userBets;
    }

    /*public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public TypeOfBet getTypeOfBet() {
        return typeOfBet;
    }

    public void setTypeOfBet(TypeOfBet typeOfBet) {
        this.typeOfBet = typeOfBet;
    }*/
}
