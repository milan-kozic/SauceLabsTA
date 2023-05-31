package objects;

import com.google.gson.annotations.SerializedName;
import data.HeroClass;
import org.testng.Assert;
import utils.DateTimeUtils;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Hero {

    private static final String[] heroClasses = {HeroClass.WARRIOR, HeroClass.GUARDIAN, HeroClass.REVENANT, HeroClass.ENGINEER, HeroClass.THIEF, HeroClass.RANGER, HeroClass.ELEMENTALIST, HeroClass.NECROMANCER, HeroClass.MESMER};
    private static final int maxHeroNameLength = 25;

    //@Expose
    @SerializedName("name")
    private String heroName;

    @SerializedName("type")
    private String heroClass;

    @SerializedName("level")
    private Integer heroLevel;

    @SerializedName("username")
    private String username;

    @SerializedName("createdAt")
    private Long createdAt;

    private Hero() {
        clearAllDetails();
    }

    private Hero(Hero hero) {
        this.setHeroName(hero.getHeroName());
        this.setHeroClass(hero.getHeroClass());
        this.setHeroLevel(hero.getHeroLevel());
        this.setUsername(hero.getUsername());
        this.setCreatedAt(hero.getCreatedAt());
    }

    private Hero(String heroName, String heroClass, int heroLevel, String username, Date createdAt) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(username);
        this.setCreatedAt(createdAt);
    }

    private Hero(String heroName, String heroClass, int heroLevel, String username) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(username);
        this.setCreatedAt(null);
    }

    private Hero(String heroName, String heroClass, int heroLevel) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(null);
        this.setCreatedAt(null);
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String name) {
        if (name != null) {
            Assert.assertTrue(name.length() <= maxHeroNameLength, "Invalid Hero Name '" + name + "'! Maximum number of characters for Hero Name is " + maxHeroNameLength + "!");
        }
        this.heroName = name;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String type) {
        this.heroClass = type;
    }

    public int getHeroLevel() {
        if (heroLevel == null) {
            return -1;
        }
        return heroLevel;
    }

    public void setHeroLevel(int level) {
        this.heroLevel = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedAt() {
        if (createdAt == null) {
            return null;
        }
        return DateTimeUtils.getDateTime(this.createdAt);
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

    public void clearHeroName() {
        this.heroName = null;
    }

    public void clearHeroLevel() {
        this.heroLevel = null;
    }

    public void clearHeroClass() {
        this.heroClass = null;
    }

    public void clearUsername() {
        this.username = null;
    }

    public void clearCreatedAt() {
        this.createdAt = null;
    }

    private void clearAllDetails() {
        clearHeroName();
        clearHeroLevel();
        clearHeroClass();
        clearUsername();
        clearCreatedAt();
    }

    public static String createRandomHeroClass() {
        Random random = new Random();
        int i = random.nextInt(9);
        return heroClasses[i];
    }

    public static int createRandomHeroLevel() {
        Random random = new Random();
        return random.nextInt(81);
    }

    public static Hero createNewUniqueHero(String sHeroName, String sHeroClass, int iHeroLevel) {
        String heroName = sHeroName + DateTimeUtils.getDateTimeStamp();
        return new Hero(heroName, sHeroClass, iHeroLevel);
    }

    public static Hero createNewUniqueHero(String sHeroName, String sHeroClass) {
        int heroLevel = createRandomHeroLevel();
        return createNewUniqueHero(sHeroName, sHeroClass, heroLevel);
    }

    public static Hero createNewUniqueHero(String sHeroName, int iHeroLevel) {
        String heroClass = createRandomHeroClass();
        return createNewUniqueHero(sHeroName, heroClass, iHeroLevel);
    }

    public static Hero createNewUniqueHero(String sHeroName) {
        String heroClass = createRandomHeroClass();
        int heroLevel = createRandomHeroLevel();
        return createNewUniqueHero(sHeroName, heroClass, heroLevel);
    }

    public static Hero createNewUniqueHero(User user, String sHeroName, String sHeroClass, int iHeroLevel) {
        String heroName = sHeroName + DateTimeUtils.getDateTimeStamp();
        String username = user.getUsername();
        return new Hero(heroName, sHeroClass, iHeroLevel, username);
    }

    public static Hero createNewUniqueHero(User user, String sHeroName, String sHeroClass) {
        int heroLevel = createRandomHeroLevel();
        return createNewUniqueHero(user, sHeroName, sHeroClass, heroLevel);
    }

    public static Hero createNewUniqueHero(User user, String sHeroName, int iHeroLevel) {
        String heroClass = createRandomHeroClass();
        return createNewUniqueHero(user, sHeroName, heroClass, iHeroLevel);
    }

    public static Hero createNewUniqueHero(User user, String sHeroName) {
        String heroClass = createRandomHeroClass();
        int heroLevel = createRandomHeroLevel();
        return createNewUniqueHero(user, sHeroName, heroClass, heroLevel);
    }

    public static Hero createHero(String sHeroName, String sHeroClass, int iHeroLevel, String sUsername, Date dCreatedAt) {
        return new Hero(sHeroName, sHeroClass, iHeroLevel, sUsername, dCreatedAt);
    }

    public static Hero createHero(Hero hero) {
        return new Hero(hero);
    }

    public static Hero createHeroTemplate() {
        return new Hero();
    }

    @Override
    public String toString() {
        return "Hero {"
                + "Name: " + this.heroName + ", "
                + "Class: " + this.heroClass + ", "
                + "Level: " + this.heroLevel + ", "
                + "Username: " + this.username + ", "
                + "Created at: " + this.createdAt + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return Objects.equals(getHeroName(), hero.getHeroName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeroName());
    }
}
