package erdos.demo.models.words;

import java.io.Serializable;
import java.util.Objects;

public abstract class Word implements Serializable {
    private String english;
    private String russian;
    public Word(String english, String russian) {
        this.english = english;
        this.russian = russian;
    }

    public String getRussian() {
        return russian;
    }
    public String getEnglish() {
        return english;
    }


    @Override
    public String toString() {
        return english + " | " + russian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(english, word.english) &&
                Objects.equals(russian, word.russian) &&
                Objects.equals(getClass().getSimpleName(), word.getClass().getSimpleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(english, russian, getClass().getSimpleName());
    }
}