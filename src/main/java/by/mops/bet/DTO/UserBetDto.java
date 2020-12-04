package by.mops.bet.DTO;

public class UserBetDto {
    private Long id;
    private Double value;
    private String status;
    private Long bet_id;
    private Long user_id;

    public UserBetDto(Long id, Double value, String status, Long bet_id, Long user_id) {
        this.id = id;
        this.value = value;
        this.status = status;
        this.bet_id = bet_id;
        this.user_id = user_id;
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
        return "UserBetDto{" +
                "id=" + id +
                ", value=" + value +
                ", status='" + status + '\'' +
                ", bet_id=" + bet_id +
                ", user_id=" + user_id +
                '}';
    }
}
