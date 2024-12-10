import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        try {
            // Load configuration
            Configuration config;
            System.out.println("Load configuration from file? (yes/no): ");
            if (new java.util.Scanner(System.in).nextLine().equalsIgnoreCase("yes")) {
                config = Configuration.loadConfig("config.json");
            } else {
                config = Configuration.inputConfig();
                config.saveConfig("config.json");
            }

            config.displayConfig();

            // Parameters from configuration
            int totalTickets = config.getTotalTickets();
            int ticketReleaseRate = config.getTicketReleaseRate();
            int customerRetrievalRate = config.getCustomerRetrievalRate();
            int maxCapacity = config.getMaxCapacity();
            int totalScreens = config.getScreens();

            TicketPool ticketPool = new TicketPool(maxCapacity);
            ticketPool.setTotalTickets(totalTickets);
            CountDownLatch latch = new CountDownLatch(totalScreens);
            List<Vendor> vendors = new ArrayList<>();
            List<Customer> customers = new ArrayList<>();
            List<Thread> threads = new ArrayList<>();

            // Create vendor threads
            for (int screen = 1; screen <= totalScreens; screen++) {
                int ticketsPerVendor = totalTickets / totalScreens;
                Vendor vendor = new Vendor(ticketPool, screen, ticketsPerVendor, ticketReleaseRate, latch);
                vendors.add(vendor);
                Thread vendorThread = new Thread(vendor, "Vendor-" + screen);
                threads.add(vendorThread);
                vendorThread.start();
            }

            // Create customer threads
            for (int screen = 1; screen <= totalScreens; screen++) {
                Customer customer = new Customer(ticketPool, screen, customerRetrievalRate, latch);
                customers.add(customer);
                Thread customerThread = new Thread(customer, "Customer-Screen-" + screen);
                threads.add(customerThread);
                customerThread.start();
            }

            latch.await(); // Wait until vendors finish adding tickets

            // Wait for all tickets to be sold
            synchronized (ticketPool) {
                while (!ticketPool.areAllTicketsSold()) {
                    ticketPool.wait(500); // Wait periodically for updates
                }
            }

            // Stop all vendor and customer threads
            vendors.forEach(Vendor::stop);
            customers.forEach(Customer::stop);
            threads.forEach(Thread::interrupt);

            System.out.println("System shutdown. Final ticket status:");
            for (int i = 1; i <= totalScreens; i++) {
                System.out.println("Screen " + i + ": " + ticketPool.getRemainingTickets(i) + " tickets remaining.");
            }
        } catch (IOException e) {
            System.err.println("Error loading or saving configuration: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("System interrupted. Exiting...");
            Thread.currentThread().interrupt();
        }
    }
}
