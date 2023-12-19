import tasks.data.Contact;
import tasks.data.Contacts;
import tasks.data.EmailContact;
import tasks.data.SocialMediaContact;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class ContactsTest {

    private Contacts contacts;

    @Before
    public void setUp() {
        contacts = new Contacts();
        contacts.add(new Contact("Stepan", "Kysil", "123-456-7890"));
        contacts.add(new EmailContact("Ivan", "Kysil", "123-456-7890", "kysil@gmail.com"));
        contacts.add(new SocialMediaContact("Petro", "Kysil", "123-456-7123", "kysilpetro@gmail.com", "kysil123"));
        contacts.add(new SocialMediaContact("Ivan", "Kysil", "123-456-7890", "kysil@gmail.com", "kysil456"));
    }

    @Test
    public void testFindByName() {
        String name = "Stepan";
        String[] result = contacts.findByName(name).toArray(String[]::new);
        String[] expected = {"123-456-7890"};
        assertEquals(expected, result);
    }

    @Test
    public void testFindByEmailAndPhone() {
        String email = "kysilpetro@gmail.com";
        String phone = "123-456-7123";
        String[] result = contacts.findByEmailAndPhone(email, phone).toArray(String[]::new);
        String[] expected = {"Kysil"};
        assertEquals(expected, result);
    }

    @Test
    public void testSortByName() {
        String[] result = contacts.sortByName().stream().map(Contact::getName).toArray(String[]::new);
        String[] expected = {"Ivan", "Ivan", "Petro", "Stepan"};
        assertEquals(expected, result);
    }

    @Test
    public void testMergeContacts() {
        Contact resultContact = contacts.mergeContacts(1, 3);
        String[] result = {
                resultContact.getName(),
                resultContact.getSurname(),
                resultContact.getPhone(),
                ((EmailContact) resultContact).getEmail(),
                ((SocialMediaContact) resultContact).getNickname()
        };
        String[] expected = {"Ivan", "Kysil", "123-456-7890", "kysil@gmail.com", "kysil456"};
        assertEquals(expected, result);
    }
}


