package tasks.data;

import java.util.*;

public class Contacts {
    private List<Contact> contacts;

    public Contacts() {
        this.contacts = new ArrayList<>();
    }

    public void add(Contact contact) {
        contacts.add(contact);
    }

    public List<String> findByName(String name) {
        List<String> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().startsWith(name)) {
                result.add(contact.getPhone());
            }
        }
        return result;
    }

    public List<String> findByEmailAndPhone(String email, String phone) {
        List<String> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact instanceof EmailContact) {
                EmailContact emailContact = (EmailContact) contact;
                if (emailContact.getEmail().equals(email) || emailContact.getPhone().equals(phone)) {
                    result.add(contact.getSurname());
                }
            }
        }
        return result;
    }

    public List<Contact> sortByName() {
        List<Contact> sortedContacts = new ArrayList<>(contacts);
        Collections.sort(sortedContacts, Comparator.comparing(Contact::getName));
        return sortedContacts;
    }

    public void printContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public Contact mergeContacts(int index1, int index2) {
        Contact contact1 = contacts.get(index1);
        Contact contact2 = contacts.get(index2);

        if (contact1 != null && contact2 != null && (contact1.getName().equals(contact2.getName()) || contact1.getSurname().equals(contact2.getSurname()))) {
            String mergedContactPhone = contact1.getPhone().equals(contact2.getPhone()) ? contact1.getPhone() : contact1.getPhone() + " & " + contact2.getPhone();
            String mergedContactEmail = getMergedEmail(contact1, contact2);
            String mergedContactNickname = getMergedNickname(contact1, contact2);

            Contact mergedContact = createMergedContact(contact1, mergedContactPhone, mergedContactEmail, mergedContactNickname);

            contacts.add(mergedContact);
            contacts.remove(contact1);
            contacts.remove(contact2);
            return mergedContact;
        }

        return null;
    }

    private String getMergedEmail(Contact contact1, Contact contact2) {
        if (contact1 instanceof EmailContact && contact2 instanceof EmailContact) {
            if(!Objects.equals(((EmailContact) contact1).getEmail(), ((EmailContact) contact2).getEmail())){
            return ((EmailContact) contact1).getEmail() + " & " + ((EmailContact) contact2).getEmail();}
            else return  ((EmailContact) contact1).getEmail();
        } else if (contact1 instanceof EmailContact) {
            return ((EmailContact) contact1).getEmail();
        } else if (contact2 instanceof EmailContact) {
            return ((EmailContact) contact2).getEmail();
        }
        return "";
    }

    private String getMergedNickname(Contact contact1, Contact contact2) {
        if (contact1 instanceof SocialMediaContact && contact2 instanceof SocialMediaContact) {
            return ((SocialMediaContact) contact1).getNickname() + " & " + ((SocialMediaContact) contact2).getNickname();
        } else if (contact1 instanceof SocialMediaContact) {
            return ((SocialMediaContact) contact1).getNickname();
        } else if (contact2 instanceof SocialMediaContact) {
            return ((SocialMediaContact) contact2).getNickname();
        }
        return "";
    }

    private Contact createMergedContact(Contact contact1, String mergedContactPhone, String mergedContactEmail, String mergedContactNickname) {
        if (!mergedContactEmail.isEmpty()) {
            if (!mergedContactNickname.isEmpty()) {
                return new SocialMediaContact(
                        contact1.getName(),
                        contact1.getSurname(),
                        mergedContactPhone,
                        mergedContactEmail,
                        mergedContactNickname
                );
            } else {
                return new EmailContact(
                        contact1.getName(),
                        contact1.getSurname(),
                        mergedContactPhone,
                        mergedContactEmail
                );
            }
        } else {
            return new Contact(
                    contact1.getName(),
                    contact1.getSurname(),
                    mergedContactPhone
            );
        }
    }

}
