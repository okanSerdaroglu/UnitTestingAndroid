package com.example.unittestingandroid.repository;

import com.example.unittestingandroid.persistence.NoteDao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// if you use @BeforeAll annotation you have to use this
public class NoteRepositoryTest {

    // system under test
    private NoteRepository noteRepository;

    //@Mock
    private NoteDao noteDao;


    @BeforeEach // individual test
    public void initEach() {
        //MockitoAnnotations.initMocks(this); // these are the same
        noteDao = Mockito.mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }


    /**
     * @BeforeAll // for all class
     * public void init() {
     * noteDao = Mockito.mock(NoteDao.class);
     * noteRepository = new NoteRepository(noteDao);
     * }
     **/

    @Test
    void dummyTest() throws Exception {

        Assertions.assertNotNull(noteDao);
        Assertions.assertNotNull(noteRepository);

    }
}
