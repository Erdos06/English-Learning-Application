package erdos.demo.services;

import erdos.demo.models.words.*;

import java.io.*;
import java.util.LinkedList;

public class WordService<T extends Word> {
    public void saveWord(T word) throws IOException {
        LinkedList<T> existingWords = readWords();
        existingWords.add(word);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("demo/txt-files/words.dat"))) {
            oos.writeObject(existingWords);
        }
        System.out.println("Word saved: " + word);
    }

    public LinkedList<T> readWords() throws IOException {
        File file = new File("demo/txt-files/words.dat");
        LinkedList<T> wordList = new LinkedList<>();

        if (!file.exists() || file.length() == 0) {
            return wordList;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("demo/txt-files/words.dat"))) {
            wordList = (LinkedList<T>) ois.readObject(); // приравниваем прочитанный лист
        } catch (ClassNotFoundException e) {
            throw new IOException("Ошибка при чтении файла", e);
        }

        return wordList;
    }

    public int getSizeOfWords() throws IOException {
        if(readWords().isEmpty()){
            return 1;
        }
        return readWords().size();
    }

    public void saveList(LinkedList<T> words) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("demo/txt-files/words.dat"))) {
            oos.writeObject(words);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}