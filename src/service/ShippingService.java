package service;

public class ShippingService {
    private static ShippingService service = null;

    private ShippingService() {
    }

    public static ShippingService getService() {
        if (service == null) {
            service = new ShippingService();
        }
        return service;
    }

    public void shipBook(String ISBN, int quantity, String email) {
        System.out.println("Shipping book with ISBN: " + ISBN);
    }

}
