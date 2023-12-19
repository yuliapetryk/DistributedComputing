package tasks.data;

public class EmailContact extends Contact {
    private String email;

    public EmailContact(String name, String surname, String phone, String email) {
        super(name, surname, phone);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return super.toString() + " Email: " + email;
    }
}