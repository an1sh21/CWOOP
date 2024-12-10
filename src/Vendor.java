import java.util.concurrent.CountDownLatch;

/**
 * Represents a vendor responsible for adding tickets to the ticket pool.
 * Operates as a runnable to allow concurrent execution.
 */
public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int screenNumber;
    private final int ticketsToAdd;
    private final int releaseRate;
    private final CountDownLatch latch; // Synchronizes with customers
    private volatile boolean running = true;

    /**
     * Constructs a Vendor instance.
     *
     * @param ticketPool   The ticket pool to which tickets will be added.
     * @param screenNumber The screen for which tickets are being added.
     * @param ticketsToAdd The total number of tickets to add.
     * @param releaseRate  The number of tickets to release per second.
     * @param latch        A latch to signal when the vendor has finished adding tickets.
     */
    public Vendor(TicketPool ticketPool, int screenNumber, int ticketsToAdd, int releaseRate, CountDownLatch latch) {
        this.ticketPool = ticketPool;
        this.screenNumber = screenNumber;
        this.ticketsToAdd = ticketsToAdd;
        this.releaseRate = releaseRate;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            int ticketsAdded = 0;

            while (running && ticketsAdded < ticketsToAdd && !ticketPool.areAllTicketsSold()) {
                // Add tickets in batches based on the release rate
                for (int i = 0; i < releaseRate && ticketsAdded < ticketsToAdd; i++) {
                    if (!running || ticketPool.areAllTicketsSold()) {
                        break;
                    }

                    Ticket ticket = new Ticket(screenNumber, "Seat-" + (ticketsAdded + 1), "10:00 AM", "Not Booked");
                    ticketPool.addTicket(screenNumber, ticket);
                    ticketsAdded++;
                }

                // Simulate delay between releasing ticket batches
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown(); // Ensure latch is decremented regardless of success or interruption
        }
    }

    /**
     * Stops the vendor's ticket addition process.
     */
    public void stop() {
        running = false;
    }
}
