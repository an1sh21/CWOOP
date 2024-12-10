import java.util.concurrent.CountDownLatch;

/**
 * Represents a customer attempting to purchase tickets from a specific screen.
 * Operates as a runnable to allow concurrent execution.
 */
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int screenNumber;
    private final int retrievalRate;
    private final CountDownLatch latch; // Wait until all vendors finish adding tickets
    private volatile boolean running = true;

    /**
     * Constructs a Customer instance.
     *
     * @param ticketPool   The ticket pool from which tickets will be retrieved.
     * @param screenNumber The screen for which the customer is purchasing tickets.
     * @param retrievalRate The number of tickets the customer attempts to retrieve per second.
     * @param latch        A latch to synchronize the start of ticket retrieval.
     */
    public Customer(TicketPool ticketPool, int screenNumber, int retrievalRate, CountDownLatch latch) {
        this.ticketPool = ticketPool;
        this.screenNumber = screenNumber;
        this.retrievalRate = retrievalRate;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            // Wait until all vendors are done adding tickets
            latch.await();

            while (running && !ticketPool.areAllTicketsSold()) {
                for (int i = 0; i < retrievalRate; i++) {
                    if (!running || ticketPool.areAllTicketsSold()) {
                        break;
                    }

                    Ticket ticket = ticketPool.removeTicket(screenNumber);
                    if (ticket != null) {
                        System.out.println(Thread.currentThread().getName() + " purchased " + ticket);
                    } else {
                        break; // Stop if no tickets are available
                    }
                }

                // Simulate delay between ticket retrieval attempts
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Stops the customer's ticket purchasing activity.
     */
    public void stop() {
        running = false;
    }
}
