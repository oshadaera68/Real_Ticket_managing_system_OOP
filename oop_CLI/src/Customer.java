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
            for (int i = 0; i < customerRetrivalRate; i++) {
                Ticket ticket = ticketPool.removeTicket();
                System.out.println("Customer retrieved: " + ticket);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
