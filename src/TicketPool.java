import java.util.*;

/**
 * Represents a pool of tickets organized by screen.
 * Provides synchronized methods for adding and retrieving tickets in a thread-safe manner.
 */
public class TicketPool {
    private final Map<Integer, Queue<Ticket>> ticketsByScreen = new HashMap<>();
    private final int maxCapacity;
    private int totalTicketsRemaining;

    /**
     * Constructs a TicketPool with a specified maximum capacity per screen.
     *
     * @param maxCapacity The maximum number of tickets per screen.
     */
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.totalTicketsRemaining = 0;
    }

    /**
     * Sets the total number of tickets in the pool.
     *
     * @param totalTickets The total number of tickets.
     */
    public synchronized void setTotalTickets(int totalTickets) {
        this.totalTicketsRemaining = totalTickets;
    }

    /**
     * Adds a ticket to the pool for a specified screen.
     * Waits if the screen has reached its maximum capacity.
     *
     * @param screenNumber The screen to which the ticket belongs.
     * @param ticket       The ticket to be added.
     */
    public synchronized void addTicket(int screenNumber, Ticket ticket) {
        ticketsByScreen.putIfAbsent(screenNumber, new LinkedList<>());
        Queue<Ticket> screenTickets = ticketsByScreen.get(screenNumber);

        // Wait if the screen has reached max capacity
        while (screenTickets.size() >= maxCapacity) {
            try {
                System.out.println("Screen " + screenNumber + " has reached max capacity. Vendor waiting...");
                wait(); // Wait until space is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Add the ticket and notify all waiting threads
        screenTickets.add(ticket);
        totalTicketsRemaining++;
        System.out.println("Added " + ticket + " to Screen " + screenNumber);
        notifyAll();
    }

    /**
     * Removes a ticket from the pool for a specified screen.
     * Waits if no tickets are available for the screen.
     *
     * @param screenNumber The screen from which to retrieve a ticket.
     * @return The retrieved ticket, or null if no tickets are available.
     */
    public synchronized Ticket removeTicket(int screenNumber) {
        Queue<Ticket> screenTickets = ticketsByScreen.get(screenNumber);

        // Wait if there are no tickets for this screen
        while (screenTickets == null || screenTickets.isEmpty()) {
            if (totalTicketsRemaining == 0) {
                return null; // No tickets remaining
            }
            try {
                System.out.println("Screen " + screenNumber + " has no tickets. Customer waiting...");
                wait(); // Wait until tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        // Retrieve the ticket, mark it as booked, and notify all
        Ticket ticket = screenTickets.poll();
        ticket.setStatus("Booked");
        totalTicketsRemaining--;

        if (totalTicketsRemaining == 0) {
            System.out.println("All tickets are sold out!");
        }

        notifyAll();
        return ticket;
    }

    /**
     * Checks if all tickets in the pool are sold out.
     *
     * @return True if all tickets are sold out; false otherwise.
     */
    public synchronized boolean areAllTicketsSold() {
        return totalTicketsRemaining == 0;
    }

    /**
     * Retrieves the number of remaining tickets for a specified screen.
     *
     * @param screenNumber The screen for which to check remaining tickets.
     * @return The number of remaining tickets for the screen.
     */
    public synchronized int getRemainingTickets(int screenNumber) {
        Queue<Ticket> screenTickets = ticketsByScreen.get(screenNumber);
        return screenTickets == null ? 0 : screenTickets.size();
    }
}
