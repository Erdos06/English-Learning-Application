package erdos.demo.models.users;

import erdos.demo.models.words.Word;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable {
    private String username;
    private String password;
    private Set<String> progress = new HashSet<>();
    private static User currentUser;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public void setProgress(Word word) {
        progress.add(word.getEnglish());
    }

    public void minusProgress(Word word) {
        progress.remove(word.getEnglish());
    }

    public int getSizeOfProgress() {
        if(progress.isEmpty()){
            return 0;
        }
        return progress.size();
    }

    public static User getCurrentUser() { return currentUser; }


    @Override
    public String toString() {
        return username + " | " + password + " | " + progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}