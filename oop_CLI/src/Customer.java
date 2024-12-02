/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Customer implements Runnable {
    private final int customerRetrivalRate;
    private final TicketPool ticketPool;

    public Customer(int customerRetrivalRate, TicketPool ticketPool) {
        this.customerRetrivalRate = customerRetrivalRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                for (int i = 0; i < customerRetrivalRate; i++) {
                    Ticket ticket = ticketPool.removeTicket(); // removing the tickets
                    if (ticket == null) {
                        return;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
