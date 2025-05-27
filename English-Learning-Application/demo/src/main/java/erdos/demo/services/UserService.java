package erdos.demo.services;

import erdos.demo.models.users.User;
import erdos.demo.models.words.Word;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class UserService {
    public void saveUser(User user) throws IOException {
        Set<User> users = readUsers();
        users.add(user);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("demo/txt-files/users.dat"))) {
            oos.writeObject(users);
        }
        System.out.println("User saved: " + user);
    }

    public Set<User> readUsers() throws IOException {
        File file = new File("demo/txt-files/users.dat");
        Set<User> userSet = new HashSet<>();

        if (!file.exists() || file.length() == 0) {
            return userSet;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            userSet = (Set<User>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new IOException("Ошибка при чтении файла", e);
        }

        return userSet;
    }

    public void saveUserProgress(User user, Word currentWord) throws IOException {
        Set<User> users = readUsers();

        for(User u : users) {
            if(user.getUsername().equals(u.getUsername())) {
                System.out.println("*  +1   *");
                u.setProgress(currentWord);
                User.setCurrentUser(u);
                break;
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("demo/txt-files/users.dat"))) {
            oos.writeObject(users);
        }
    }
}