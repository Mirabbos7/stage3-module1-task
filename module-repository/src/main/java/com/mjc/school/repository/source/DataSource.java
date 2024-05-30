package com.mjc.school.repository.source;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.util.DataReader;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DataSource {

    private final static DataSource instance = new DataSource();

    private final String AUTHORS_FILE_NAME = "author.txt";
    private final String CONTENT_FILE_NAME = "content.txt";
    private final String TITLES_FILE_NAME = "titles.txt";

    public static DataSource getInstance() {
        return DataSource.instance;
    }

    private DataSource() {

    }

    public List<Author> readAuthors() {
        List<Author> authorsList = new LinkedList<>();
        List<String> authorsLines = DataReader.read(AUTHORS_FILE_NAME);
        for (String author : authorsLines) {
            authorsList.add(new Author(author));
        }
        return authorsList;
    }

    public List<News> readNews() {
        List<News> newsList = new LinkedList<>();

        List<Author> authorsList = readAuthors();
        List<String> titlesLines = DataReader.read(TITLES_FILE_NAME);
        List<String> contentLines = DataReader.read(CONTENT_FILE_NAME);


        Random random = new SecureRandom();
        for (long i = 1; i <= 20; i++) {
            newsList.add(new News(
                    titlesLines.get(random.nextInt(titlesLines.size())),
                    contentLines.get(random.nextInt(contentLines.size())),
                    LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                    authorsList.get(random.nextInt(authorsList.size())).getId()));
        }

        return newsList;
    }
}
