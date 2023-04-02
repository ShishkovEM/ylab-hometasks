package io.ylab.intensive.lesson05.messagefilter;

public interface BadWordsRepository {
    void createTableIfNotExists();
    void loadWordsFromFile();
    boolean wordExists(String word);
}