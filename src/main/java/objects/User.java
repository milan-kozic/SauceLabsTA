package objects;

import org.testng.Assert;
import utils.DateTimeUtils;
import utils.PropertiesUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User {

    private static final int maxUsernameLength = 35;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String about;
    private String secretQuestion;
    private String secretAnswer;
    private Long createdAt;
    private Integer heroCount;
    private List<Hero> heroes;

    private User() {
        clearAllDetails();
    }

    private User(User user) {
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setAbout(user.getAbout());
        this.setSecretQuestion(user.getSecretQuestion());
        this.setSecretAnswer(user.getSecretAnswer());
        this.setCreatedAt(user.getCreatedAt());
        this.setHeroes(user.getHeroes());
    }

    private User(String username, String password, String email, String firstName, String lastName, String about, String secretQuestion, String secretAnswer, Date createdAt, List<Hero> heroes) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAbout(about);
        this.setSecretQuestion(secretQuestion);
        this.setSecretAnswer(secretAnswer);
        this.setCreatedAt(createdAt);
        this.setHeroes(heroes);
    }

    private User(String username, String password, String email, String firstName, String lastName, String about, String secretQuestion, String secretAnswer) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAbout(about);
        this.setSecretQuestion(secretQuestion);
        this.setSecretAnswer(secretAnswer);
        this.setCreatedAt(null);
        this.setHeroes(null);
    }

    private User(String username) {
        String sPassword = PropertiesUtils.getDefaultPassword();
        String sEmail = username + "@mail.com";
        String sFirstName = "Name";
        String sLastName = "Surname";
        String sAbout = "About Me";
        String sSecretQuestion = "Question?";
        String sSecretAnswer = "Answer";
        this.setUsername(username);
        this.setPassword(sPassword);
        this.setEmail(sEmail);
        this.setFirstName(sFirstName);
        this.setLastName(sLastName);
        this.setAbout(sAbout);
        this.setSecretQuestion(sSecretQuestion);
        this.setSecretAnswer(sSecretAnswer);
        this.setCreatedAt(null);
        this.setHeroes(null);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getAbout() {
        return about;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public Date getCreatedAt() {
        if (this.createdAt == null) {
            return null;
        }
        return DateTimeUtils.getDateTime(this.createdAt);
    }

    public int getHeroCount() {
        return heroCount;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public Hero getHero(String sHeroName) {
        boolean bFound = false;
        Hero hero = null;
        if (heroes != null) {
            for (Hero value : heroes) {
                hero = value;
                if (hero.getHeroName().equals(sHeroName)) {
                    bFound = true;
                    break;
                }
            }
        }
        Assert.assertTrue(bFound, "User '" + getUsername() + "' doesn't have Hero '" + sHeroName + "'!");
        return hero;
    }

    public Hero getHero(int index) {
        Assert.assertTrue((index >= 0) && (index < getHeroCount()), "Cannot get Hero with index " + index + " for User '" + getUsername() + "'! User has " + getHeroCount() + " hero(es)!");
        return heroes.get(index);
    }

    public void setUsername(String username) {
        if (username != null) {
            Assert.assertTrue(username.length() <= maxUsernameLength, "Invalid Username '" + username + "'! Maximum number of characters for username is " + maxUsernameLength + "!");
        }
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(Date date) {
        if (date == null) {
            this.createdAt = null;
        } else {
            this.createdAt = date.getTime();
        }
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
        if (heroes == null) {
            this.heroCount = 0;
        } else {
            this.heroCount = heroes.size();
        }
    }

    public void clearUsername() {
        this.username = null;
    }

    public void clearPassword() {
        this.password = null;
    }

    public void clearEmail() {
        this.email = null;
    }

    public void clearFirstName() {
        this.firstName = null;
    }

    public void clearLastName() {
        this.lastName = null;
    }

    public void clearAbout() {
        this.about = null;
    }

    public void clearSecretQuestion() {
        this.secretQuestion = null;
    }

    public void clearSecretAnswer() {
        this.secretAnswer = null;
    }

    public void clearCreatedAt() {
        this.createdAt = null;
    }

    public void clearHeroes() {
        this.heroes = null;
        this.heroCount = 0;
    }

    private void clearAllDetails() {
        clearUsername();
        clearPassword();
        clearEmail();
        clearFirstName();
        clearLastName();
        clearAbout();
        clearSecretQuestion();
        clearSecretAnswer();
        clearCreatedAt();
        clearHeroes();
    }

    public void addHero(Hero hero) {
        if (heroes == null) {
            heroes = new ArrayList<>();
        }
        if (!heroes.contains(hero)) {
            hero.setUsername(username);
            heroes.add(hero);
            heroCount = heroes.size();
        } else {
            Assert.fail("User '" + getUsername() + "' cannot have two heroes with the same name '" + hero.getHeroName() + "'!");
        }
    }

    public void updateHero(Hero hero) {
        if (heroes.contains(hero)) {
            int index = heroes.indexOf(hero);
            Hero oldHero = heroes.get(index);
            if (hero.getUsername().equals(oldHero.getUsername())) {
                heroes.set(index, hero);
            } else {
                Assert.fail("Username of user '" + getUsername() + "' that owns hero '" + hero.getHeroName() + "' cannot be updated to '" + hero.getUsername() + "'!");
            }
        } else {
            Assert.fail("User '" + getUsername() + "' doesn't have hero with the name '" + hero.getHeroName() + "'!");
        }
    }

    public void removeHero(Hero hero) {
        if (heroes.contains(hero)) {
            heroes.remove(hero);
            heroCount = heroes.size();
        } else {
            Assert.fail("User '" + getUsername() + "' doesn't have hero with the name '" + hero.getHeroName() + "'!");
        }
    }

    public static User createNewUniqueUser(String sUsername) {
        String username = sUsername.toLowerCase() + DateTimeUtils.getDateTimeStamp();
        return new User(username);
    }

    public static User createUser(String sUsername, String sPassword, String sEmail, String sFirstName, String sLastName, String sAbout, String sSecretQuestion, String sSecretAnswer, Date dCreatedAt, List<Hero> heroes) {
        return new User(sUsername, sPassword, sEmail, sFirstName, sLastName, sAbout, sSecretQuestion, sSecretAnswer, dCreatedAt, heroes);
    }

    public static User createUser(User user) {
        return new User(user);
    }

    public static User createUserTemplate() {
        return new User();
    }

    @Override
    public String toString() {
        return "User {"
                + "Username: " + getUsername() + ", "
                + "Password: " + getPassword() + ", "
                + "Email: " + getEmail() + ", "
                + "First Name: " + getFirstName() + ", "
                + "Last Name: " + getLastName() + ", "
                + "About: " + getAbout() + ", "
                + "Secret Question: " + getSecretQuestion() + ", "
                + "Secret Answer: " + getSecretAnswer() + ", "
                + "Created at: " + getCreatedAt() + ", "
                + "Hero Count: " + getHeroCount() + ", "
                + "Heroes: " + getHeroes() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
