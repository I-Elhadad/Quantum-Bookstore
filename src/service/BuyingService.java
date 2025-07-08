package service;
import java.util.HashMap;
import model.Book;
import model.PaperBook;
import model.DemoBook;
import model.EBook;
public class BuyingService {
    private static BuyingService buyingService = null;

    private BuyingService() {
    }

    public static BuyingService getBuyingService() {
        if (buyingService == null) {
            buyingService = new BuyingService();
        }
        return buyingService;
    }

    public Double buyBook(String ISBN, int quantity, String email) {

        InventoryService inventory = InventoryService.getInventory();
        inventory.removeOutdatedBooks();
        Book book = inventory.getBook(ISBN);
        Double paidAmount = 0.0;
        if (book == null) {
            throw new IllegalArgumentException("Book with ISBN " + ISBN + " not found.");
        }
        if (book instanceof PaperBook) {
            PaperBook paperBook = (PaperBook) book;
            if (quantity > paperBook.getQuantity()) {
                throw new IllegalArgumentException("Requested quantity exceeds available quantity for book: " + ISBN + ". Available: " + paperBook.getQuantity() + ", Requested: " + quantity);
            }
            paidAmount = paperBook.getPrice() * quantity;
            paperBook.setQuantity(paperBook.getQuantity() - quantity);
            ShippingService shippingService = ShippingService.getService();
            shippingService.shipBook(ISBN, quantity, email);
            System.out.println("Purchased " + quantity + " copies of paper book with ISBN: " + ISBN + ". Confirmation sent to: " + email);
        } else if (book instanceof EBook) {
            paidAmount = book.getPrice();
            Mailservice mailservice = Mailservice.getMailservice();
            mailservice.sendEBook(ISBN, email);
            System.out.println("Purchased e-book with ISBN: " + ISBN + ". Confirmation sent to: " + email);
        } else if (book instanceof DemoBook) {
            throw new IllegalArgumentException("Demo books cannot be purchased. ISBN: " + ISBN);
        }
        System.out.println("Total amount paid for book with title " + book.getTitle() + ": " + paidAmount);
        return paidAmount;
    }

}

