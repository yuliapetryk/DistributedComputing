package tasks.data;

public class SocialMediaContact extends EmailContact {
    private String nickname;

    public SocialMediaContact(String name, String surname, String phone, String email, String nickname) {
        super(name, surname, phone, email);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return super.toString() + " Nickname: " + nickname;
    }
}