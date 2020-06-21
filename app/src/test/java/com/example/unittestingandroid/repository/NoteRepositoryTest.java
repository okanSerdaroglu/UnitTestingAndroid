package com.example.unittestingandroid.repository;

import static com.example.unittestingandroid.repository.NoteRepository.INSERT_FAILURE;
import static com.example.unittestingandroid.repository.NoteRepository.INSERT_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.unittestingandroid.models.Note;
import com.example.unittestingandroid.persistence.NoteDao;
import com.example.unittestingandroid.ui.Resource;
import com.example.unittestingandroid.util.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import io.reactivex.Single;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// if you use @BeforeAll annotation you have to use this
public class NoteRepositoryTest {

    private static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);

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

    /**
     *  --- Cases ---
     * insert note
     * verify the correct method is called
     * confirm observer is triggered
     * confirm new rows inserted
     */

    /**
     * --- Cases ---
     * insert a note
     * insert note with null title
     * confirm throw exception
     */

    @Test
    void insertNote_returnRows() throws Exception {

        // Arrange
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue =
                noteRepository.insertNote(NOTE1).blockingFirst();


        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value : " + returnedValue.data);
        assertEquals(Resource.success(1, INSERT_SUCCESS), returnedValue);

        // Or test using RxJava. It is the same above code.
        // Prefer the above code. It is much more organized
        /**noteRepository.insertNote(NOTE1)
         .test()
         .await() // it is the same with blogFirst
         .assertValue(Resource.success(1, INSERT_SUCCESS));**/

    }

    @Test
    void insertNote_returnFailure() throws Exception {
        // Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue =
                noteRepository.insertNote(NOTE1).blockingFirst();

        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, INSERT_FAILURE), returnedValue);
    }

    @Test
    void insertNote_nullTitle_throwException() throws Exception {

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

    }


}
