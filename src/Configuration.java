
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 * Represents the configuration settings for the ticketing system.
 * Provides methods to set, retrieve, save, and load configuration details.
 */
public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxCapacity;
    private int screens;

    /**
     * Initializes the configuration with the specified parameters.
     *
     * @param totalTickets        The total number of tickets available.
     * @param ticketReleaseRate   The rate at which tickets are released.
     * @param customerRetrievalRate The rate at which customers retrieve tickets.
     * @param maxCapacity         The maximum capacity of the ticket pool.
     * @param screens             The number of screens in the venue.
     */
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxCapacity, int screens) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxCapacity = maxCapacity;
        this.screens = screens;
    }

    /**
     * Retrieves the total number of tickets.
     *
     * @return The total number of tickets.
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Sets the total number of tickets.
     *
     * @param totalTickets The total number of tickets to set.
     */
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    /**
     * Retrieves the ticket release rate.
     *
     * @return The ticket release rate.
     */
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    /**
     * Sets the ticket release rate.
     *
     * @param ticketReleaseRate The ticket release rate to set.
     */
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    /**
     * Retrieves the customer retrieval rate.
     *
     * @return The customer retrieval rate.
     */
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    /**
     * Sets the customer retrieval rate.
     *
     * @param customerRetrievalRate The customer retrieval rate to set.
     */
    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    /**
     * Retrieves the maximum capacity.
     *
     * @return The maximum ticket capacity.
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Sets the maximum capacity.
     *
     * @param maxCapacity The maximum capacity to set.
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Retrieves the number of screens.
     *
     * @return The number of screens.
     */
    public int getScreens() {
        return screens;
    }

    /**
     * Sets the number of screens.
     *
     * @param screens The number of screens to set.
     */
    public void setScreens(int screens) {
        this.screens = screens;
    }

    /**
     * Displays the current configuration details.
     */
    public void displayConfig() {
        System.out.println("System Configuration:");
        System.out.println("Total Tickets: " + getTotalTickets());
        System.out.println("Ticket Release Rate: " + getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + getCustomerRetrievalRate());
        System.out.println("Maximum Ticket Capacity: " + getMaxCapacity());
        System.out.println("Number of Screens: " + getScreens());
    }

    /**
     * Saves the configuration settings to a JSON file.
     *
     * @param filename The name of the file to save the configuration.
     * @throws IOException If an I/O error occurs.
     */
    public void saveConfig(String filename) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json);
            System.out.println("Configuration saved to " + filename);
        }
    }

    /**
     * Creates a new configuration by gathering user input from the console.
     *
     * @return A new Configuration instance based on user input.
     */
    public static Configuration inputConfig() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Configure the System:");
        System.out.print("Enter Total Number of Tickets: ");
        int totalTickets = scanner.nextInt();

        System.out.print("Enter Ticket Release Rate (tickets per second): ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter Customer Retrieval Rate (tickets per second): ");
        int customerRetrievalRate = scanner.nextInt();

        System.out.print("Enter Maximum Ticket Capacity per Screen: ");
        int maxCapacity = scanner.nextInt();

        System.out.print("Enter Number of Screens: ");
        int screens = scanner.nextInt();

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxCapacity, screens);
    }

    /**
     * Loads configuration settings from a JSON file.
     *
     * @param fileName The name of the file containing the configuration.
     * @return The loaded Configuration instance.
     * @throws IOException If an I/O error occurs.
     */
    public static Configuration loadConfig(String fileName) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, Configuration.class);
        }
    }
}
