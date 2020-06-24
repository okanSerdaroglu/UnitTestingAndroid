package com.example.unittestingandroid.repository;

import static com.example.unittestingandroid.repository.NoteRepository.DELETE_FAILURE;
import static com.example.unittestingandroid.repository.NoteRepository.DELETE_SUCCESS;
import static com.example.unittestingandroid.repository.NoteRepository.INSERT_FAILURE;
import static com.example.unittestingandroid.repository.NoteRepository.INSERT_SUCCESS;
import static com.example.unittestingandroid.repository.NoteRepository.INVALID_NOTE_ID;
import static com.example.unittestingandroid.repository.NoteRepository.NOTE_TITLE_NULL;
import static com.example.unittestingandroid.repository.NoteRepository.UPDATE_FAILURE;
import static com.example.unittestingandroid.repository.NoteRepository.UPDATE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.lifecycle.MutableLiveData;

import com.example.unittestingandroid.models.Note;
import com.example.unittestingandroid.persistence.NoteDao;
import com.example.unittestingandroid.ui.Resource;
import com.example.unittestingandroid.util.InstantExecutorExtension;
import com.example.unittestingandroid.util.LiveDataTestUtil;
import com.example.unittestingandroid.util.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// if you use @BeforeAll annotation you have to use this
@ExtendWith(InstantExecutorExtension.class)
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
     * --- Cases ---
     * insert note
     * verify the correct method is called
     * confirm observer is triggered
     * confirm new rows inserted
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
        verify(noteDao).insertNote(any(Note.class)); // is insertNoteMethod called ?
        verifyNoMoreInteractions(noteDao); // is any other methods called ?

        System.out.println("Returned value : " + returnedValue.data);
        assertEquals(Resource.success(1, INSERT_SUCCESS), returnedValue);

        // Or test using RxJava. It is the same above code.
        // Prefer the above code. It is much more organized
        /**noteRepository.insertNote(NOTE1)
         .test()
         .await() // it is the same with blogFirst
         .assertValue(Resource.success(1, INSERT_SUCCESS));**/

    }


    /**
     * --- Cases ---
     * insert a note
     * insert note with null title
     * confirm throw exception
     */
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
        verify(noteDao).insertNote(any(Note.class)); // is insertNoteMethod called ?
        verifyNoMoreInteractions(noteDao); // is any other methods called ?

        assertEquals(Resource.error(null, INSERT_FAILURE), returnedValue);
    }

    @Test
    void insertNote_nullTitle_throwException() throws Exception {

        // assertThrows return an exception
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());

    }

    /**
     * update test methods
     */


    /**
     * update note
     * <p>
     * verify the correct method is called
     * <p>
     * confirm observer is trigger
     * <p>
     * confirm number of rows updated
     */
    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {

        // Arrange
        final int updatedRow = 1;
        when(noteDao.updateNote(any(Note.class))).thenReturn(Single.just(updatedRow));
        /** when you call noteRepository.updateNote method thenReturn 1 */

        // Act
        final Resource<Integer> returnedValue
                = noteRepository.updateNote(NOTE1).blockingFirst();
        /** call noteRepository.updateNote method */

        // Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);
        /** check if noteDao.updateNote method is called ?
         *  check if there is no more interactions in noteDao
         */

        assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnedValue);
        /** control the returned value is equal success value */

    }


    /**
     * update note
     * <p>
     * Failure (-1)
     */
    @Test
    void updateNote_returnFailure() throws Exception {

        // Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);
        when(noteDao.updateNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue
                = noteRepository.updateNote(NOTE1).blockingFirst();
        /** call noteRepository.updateNote method */

        // Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);
        /** check if noteDao.updateNote method is called ?
         *  check if there is no more interactions in noteDao
         */

        assertEquals(Resource.error(null, UPDATE_FAILURE), returnedValue);
        /** control the returned value is equal failure value */

    }


    /**
     * update note
     * null title
     * throw exception
     */
    @Test
    void updateNote_nullTitle_throw_Exception() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.updateNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());

    }

    // ----- there are 5 test cases for NoteListRepository ------


    /**
     * delete note
     * null id
     * throw exception
     */
    @Test
    void deleteNote_nullId_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setId(-1);
                noteRepository.deleteNote(note);
            }
        });
        assertEquals(INVALID_NOTE_ID, exception.getMessage());
    }

    /**
     * delete note
     * delete success
     * return Resource.Success with deleted row
     */
    @Test
    void deleteNote_deleteSuccess_returnResourceSuccess() throws Exception {
        // Arrange
        final int deletedRow = 1;
        Resource<Integer> successResponse = Resource.success(deletedRow, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        // Act
        Resource<Integer> observeResource
                = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        // Assert
        assertEquals(successResponse, observeResource);

    }

    /**
     * delete note
     * delete failure
     * return Resource.Error
     */

    @Test
    void deleteNote_deleteFailure_returnResourceError() throws Exception {
        // Arrange
        final int deletedRow = -1;
        Resource<Integer> failureResponse = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        // Act
        Resource<Integer> observeResource
                = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        // Assert
        assertEquals(failureResponse, observeResource);

    }


    /**
     * retrieve notes
     * return list of notes
     */
    @Test
    void getNotes_returnListWithNotes() throws Exception {
        // Arrange
        List<Note> notes = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        // Act
        List<Note> observedData = listLiveDataTestUtil.getValue(noteRepository.getNotes());

        // Assert
        assertEquals(notes, observedData);

    }

    /**
     * retrieve notes
     * return empty list
     */
    @Test
    void getNotes_returnEmptyList() throws Exception {

        // Arrange
        List<Note> notes = new ArrayList<>();
        LiveDataTestUtil<List<Note>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        // Act
        List<Note> observedData = listLiveDataTestUtil.getValue(noteRepository.getNotes());

        // Assert
        assertEquals(notes, observedData);

    }
}
