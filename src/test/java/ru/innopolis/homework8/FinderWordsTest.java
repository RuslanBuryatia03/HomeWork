package ru.innopolis.homework8;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование класса FinderWords
 * @author KhankhasaevRV
 * @since 10.02.2019
 */
public class FinderWordsTest {



    private static final int COUNT_SOURCES = 3;
    private static final int COUNT_THREAD = 2;
    private static final String PATH = "./src/test/resources/inputData/result.txt";
    private static final Logger LOGGER = LoggerFactory.getLogger(FinderWordsTest.class);
    private final String SOURCE_NAME = "file:src/test/resources/inputData/file_{0}.txt";

    FinderWords finderWords = new FinderWords(SOURCE_NAME, COUNT_SOURCES, COUNT_THREAD );

    @BeforeEach
    void setUp() {
        LOGGER.info("setup");
    }

    @BeforeAll
    static void init() {
        LOGGER.info("init ALL");
    }

    @Test
    void printTimeExecutionTest() {
        LOGGER.info("printTimeExecutionTest...");
        Date dateTest = new Date();
        String outString =  "Количество потоков - " + COUNT_THREAD + ", Количество ресурсов - " + COUNT_SOURCES + " время - ";
        assertThat(finderWords.printTimeExecution(dateTest), Matchers.startsWith(outString));
    }


    @Test
    void clearResultFileTest() throws IOException {
        LOGGER.info("clearResultFileTest...");
        File fileTmp = File.createTempFile("data", ".txt");
        assertTrue(finderWords.clearResultFile(fileTmp.getPath()));
    }

    @Test
    void writeToFileNullTest() throws IOException {
        LOGGER.info("writeToFileNullTest...");
        assertFalse(finderWords.writeToFile(null, PATH));
    }

    @Test
    void writeToFilePositiveTest() throws IOException {
        LOGGER.info("writeToFilePositiveTest...");
        assertTrue(finderWords.writeToFile(Arrays.asList("first", "second", "third"), PATH));
    }


    @Test
    void writeToFileIOExceptionTest() {
        LOGGER.info("writeToFileFileNotFoundExceptionTest...");
        String wrongPath = "C:////fff.fff";
        assertThrows(IOException.class, () -> finderWords.writeToFile(Arrays.asList("first", "second", "third"), wrongPath));
    }


    @Test
    void finderWordsCompleteTest() {
        LOGGER.info("finderWordsCompleteTest...");
        assertThat(finderWords.getOccurency().getTime(), Matchers.greaterThan(10L));
    }
}
