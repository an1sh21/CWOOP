/**
 * Represents a ticket for a specific screen, seat, and show time.
 * Includes information about the ticket's status.
 */
public class Ticket {
    private int screenNumber;
    private String seatNumber;
    private String showTime;
    private String status;

    /**
     * Constructs a new Ticket instance.
     *
     * @param screenNumber The screen number associated with the ticket.
     * @param seatNumber   The seat number for the ticket.
     * @param showTime     The show time for the ticket.
     * @param status       The current status of the ticket (e.g., "Booked", "Available").
     */
    public Ticket(int screenNumber, String seatNumber, String showTime, String status) {
        this.screenNumber = screenNumber;
        this.seatNumber = seatNumber;
        this.showTime = showTime;
        this.status = status;
    }

    /**
     * Retrieves the screen number for this ticket.
     *
     * @return The screen number.
     */
    public int getScreenNumber() {
        return screenNumber;
    }

    /**
     * Sets the screen number for this ticket.
     *
     * @param screenNumber The screen number to set.
     */
    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }

    /**
     * Retrieves the seat number for this ticket.
     *
     * @return The seat number.
     */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets the seat number for this ticket.
     *
     * @param seatNumber The seat number to set.
     */
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Retrieves the show time for this ticket.
     *
     * @return The show time.
     */
    public String getShowTime() {
        return showTime;
    }

    /**
     * Sets the show time for this ticket.
     *
     * @param showTime The show time to set.
     */
    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    /**
     * Retrieves the current status of this ticket.
     *
     * @return The ticket status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status for this ticket.
     *
     * @param status The ticket status to set (e.g., "Booked", "Available").
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the ticket object.
     *
     * @return A string displaying ticket details.
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "Screen=" + screenNumber +
                ", Seat='" + seatNumber + '\'' +
                ", ShowTime='" + showTime + '\'' +
                ", Status='" + status + '\'' +
                '}';
    }
}
