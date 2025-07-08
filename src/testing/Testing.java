package testing;
import model.*;
import service.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class Testing {

    @BeforeEach
    void resetInventory() {
        InventoryService inventory = InventoryService.getInventory();
        inventory.clearInventory();
    }

    @Test
    void testBuyPaperBook() {
        InventoryService inventory = InventoryService.getInventory();
        PaperBook paperBook = new PaperBook("123", "Java Basics", LocalDate.of(2022, 6, 1), 29.99, 10);
        inventory.addBook(paperBook);
        BuyingService buyingService = BuyingService.getBuyingService();
        double paid = buyingService.buyBook("123", 2, "user@example.com");
        assertEquals(59.98, paid);
        assertEquals(8, paperBook.getQuantity());
    }

    @Test
    void testBuyEBook() {
        InventoryService inventory = InventoryService.getInventory();
        EBook eBook = new EBook("456", "Learn Java", LocalDate.of(2021, 4, 15), 19.99, "PDF");
        inventory.addBook(eBook);
        BuyingService buyingService = BuyingService.getBuyingService();
        double paid = buyingService.buyBook("456", 1, "user@example.com");
        assertEquals(19.99, paid);
    }

    @Test
    void testBuyDemoBookShouldFail() {
        InventoryService inventory = InventoryService.getInventory();
        DemoBook demoBook = new DemoBook("789", "Demo Java", LocalDate.of(2023, 2, 1), 0.0);
        inventory.addBook(demoBook);
        BuyingService buyingService = BuyingService.getBuyingService();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                buyingService.buyBook("789", 1, "user@example.com")
        );
        assertTrue(ex.getMessage().contains("Demo books cannot be purchased"));
    }

    @Test
    void testBuyMoreThanAvailableShouldFail() {
        InventoryService inventory = InventoryService.getInventory();
        PaperBook paperBook = new PaperBook("124", "Java Advanced", LocalDate.of(2022, 7, 1), 39.99, 5);
        inventory.addBook(paperBook);
        BuyingService buyingService = BuyingService.getBuyingService();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                buyingService.buyBook("124", 20, "user@example.com")
        );
        assertTrue(ex.getMessage().contains("Requested quantity exceeds available quantity"));
    }

    @Test
    void testRemoveOutdatedBooks() {
        InventoryService inventory = InventoryService.getInventory();
        PaperBook oldBook = new PaperBook("999", "Old Java", LocalDate.of(1900, 1, 1), 10.0, 5);
        inventory.addBook(oldBook);
        var removed = inventory.removeOutdatedBooks();
        assertEquals(1, removed.size());
        assertEquals("999", removed.get(0).getISBN());
        assertNull(inventory.getBook("999"));
    }

    @Test
    void testAddBookWithNegativePrice() {
        InventoryService inventory = InventoryService.getInventory();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                inventory.addBook(new PaperBook("111", "Invalid Book", LocalDate.of(2020, 1, 1), -5.0, 3))
        );
        assertTrue(ex.getMessage().contains("Price cannot be negative"));
    }

    @Test
    void testAddBookWithEmptyISBN() {
        InventoryService inventory = InventoryService.getInventory();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                inventory.addBook(new EBook("", "No ISBN", LocalDate.of(2020, 1, 1), 10.0, "EPUB"))
        );
        assertTrue(ex.getMessage().contains("ISBN cannot be null or empty"));
    }

    @Test
    void testBuyNonExistentBook() {
        BuyingService buyingService = BuyingService.getBuyingService();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                buyingService.buyBook("000", 1, "user@example.com")
        );
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testSetNegativeQuantityShouldFail() {
        PaperBook paperBook = new PaperBook("125", "Java Patterns", LocalDate.of(2022, 8, 1), 49.99, 10);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                paperBook.setQuantity(-1)
        );
        assertTrue(ex.getMessage().contains("Quantity cannot be negative"));
    }

    @Test
    void testRemoveOutdatedBooksWhenNoneExist() {
        InventoryService inventory = InventoryService.getInventory();
        var removed = inventory.removeOutdatedBooks();
        assertEquals(0, removed.size());
    }

    @Test
    void testRemoveBookFromStock() {
        InventoryService inventory = InventoryService.getInventory();
        PaperBook paperBook = new PaperBook("321", "Richard", LocalDate.of(2021, 1, 1), 15.0, 5);
        inventory.addBook(paperBook);
        inventory.removeBook("321");
        assertNull(inventory.getBook("321"));
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                inventory.removeBook("321")
        );
        assertTrue(ex.getMessage().contains("does not exist"));
    }

}