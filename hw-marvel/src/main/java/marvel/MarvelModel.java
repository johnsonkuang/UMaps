package marvel;

import com.opencsv.bean.CsvBindByName;

/**
 * This is the Java representation of a Marvel Character
 */
public class MarvelModel{
    /**
     * name of the hero
     */
    @CsvBindByName
    private String hero;

    /**
     * book they appear in
     */
    @CsvBindByName
    private String book;

    /**
     * get the name of the hero
     *
     * @return name of the hero
     */
    public String getHero() {
        return hero;
    }

    /**
     * set the name of the hero
     *
     * @param hero name of the hero
     */
    public void setHero(String hero) {
        this.hero = hero;
    }

    /**
     * get the book the hero appears in
     *
     * @return the book the hero appears in
     */
    public String getBook() {
        return book;
    }

    /**
     * set the book the hero appears in
     *
     * @param book the book the hero appears in
     */
    public void setBook(String book) {
        this.book = book;
    }
}