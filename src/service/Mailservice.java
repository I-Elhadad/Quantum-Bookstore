package service;

public class Mailservice {
    private static Mailservice mailservice = null;

    private Mailservice() {

    }

    public static Mailservice getMailservice() {
        if (mailservice == null) {
            mailservice = new Mailservice();
        }
        return mailservice;
    }

    public void sendEBook(String ISBN, String email) {
        System.out.println("Sending e-book with ISBN: " + ISBN + " to email: " + email);
    }
}
