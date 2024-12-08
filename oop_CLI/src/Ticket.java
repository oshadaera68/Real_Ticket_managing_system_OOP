import java.math.BigDecimal;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Ticket {
    // Fields:- ticketId, event, price
    private int ticketId;
    private String event;
    private BigDecimal price;

    public Ticket(int ticketId, String event, BigDecimal price) {
        this.ticketId = ticketId;
        this.event = event;
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", event='" + event + '\'' +
                ", price=" + price +
                '}';
    }
}
