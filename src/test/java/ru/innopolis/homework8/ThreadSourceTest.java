package ru.innopolis.homework8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;


class ThreadSourceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadSourceTest.class);

    private final String[] WORDS = {"ALICE", "WORKING", "MIKE"};
    private final String SOURCE_NAME = "file:src/test/resources/inputData/file_1.txt";
    private final String SOURCE_NAME_EMPTY = "file:src/test/resources/inputData/empty.txt";

    ThreadSource threadSource;
    ThreadSource threadSourceEmpty;

    @BeforeEach
    void setUp() {
        threadSource = new ThreadSource(SOURCE_NAME, WORDS);
        threadSourceEmpty = new ThreadSource(SOURCE_NAME_EMPTY, WORDS);
        LOGGER.info("setup");
    }

    @BeforeAll
    static void init() {
        LOGGER.info("init ALL");
    }

    @Test
    void threadSourcePositiveTest() {
        List<String> findSentences = new ArrayList<>();
        findSentences.add(
                "With malice toward none; with charity for all; with firmness in the right, as God gives us to see the right, let us strive on to finish the work we are in; to bind up the nation's wounds; to care for him who shall have borne the battle, and for his widow, and his orphan--to do all which may achieve and cherish a justand lasting peace among ourselves, and with all nations.");
        findSentences.add(
                "In answer to various questions we have received on this: We are constantly working on finishing the paperwork to legallyrequest donations in all 50 states.");
        LOGGER.info("clearResultFileTest...");
        assertThat(threadSource.call(),
                contains(findSentences.toArray()));
    }

    @Test
    void threadSourceEmptySourceTest() {
        LOGGER.info("clearResultFileTest...");
        assertThat(threadSourceEmpty.call(), empty());
    }

}