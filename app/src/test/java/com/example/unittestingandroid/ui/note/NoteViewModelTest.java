package com.example.unittestingandroid.ui.note;

import com.example.unittestingandroid.repository.NoteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NoteViewModelTest {

    // system under test
    private NoteViewModel noteViewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }

    /**
     * can't observe a note that has not been set
     */

    @Test
    void observeEmptyNote_whenNoteSet() throws Exception {
        // Arrange

        // Act

        // Assert

    }


    /**
     * observe a note has been set and onChanged will trigger in activity
     */

    @Test
    void observeNote_whenSet() throws Exception {
        // Arrange

        // Act

        // Assert

    }


    /**
     * insert a new note and observe row returned
     */
    @Test
    void insertNote_returnRow() throws Exception {
        // Arrange

        // Act

        // Assert

    }

    /**
     * insert: don't return a new row without observer
     */
    @Test
    void doNotReturnInsertRow_withoutObserver() throws Exception {
        // Arrange

        // Act

        // Assert

    }

    /**
     * set note, null title, throw exception
     */
    @Test
    void setNote_nullTitle_throwException() throws Exception {
        // Arrange

        // Act

        // Assert

    }
}