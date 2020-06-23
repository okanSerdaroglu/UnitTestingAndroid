package com.example.unittestingandroid.ui.notelist;

import static com.example.unittestingandroid.repository.NoteRepository.DELETE_FAILURE;
import static com.example.unittestingandroid.repository.NoteRepository.DELETE_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import androidx.lifecycle.MutableLiveData;

import com.example.unittestingandroid.models.Note;
import com.example.unittestingandroid.repository.NoteRepository;
import com.example.unittestingandroid.ui.Resource;
import com.example.unittestingandroid.util.InstantExecutorExtension;
import com.example.unittestingandroid.util.LiveDataTestUtil;
import com.example.unittestingandroid.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(InstantExecutorExtension.class)
public class NoteListViewModelTest {

    private NoteListViewModel viewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        viewModel = new NoteListViewModel(noteRepository);
    }

    /**
     * Retrieve list of notes
     * observe list
     * return list
     */
    @Test
    void retrieveNotes_returnNotesList() throws Exception {
        // Arrange
        List<Note> returnedData = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);

        // Act
        viewModel.getNotes();
        List<Note> observeData = listLiveDataTestUtil.getValue(viewModel.observeNotes());

        // Assert
        Assertions.assertEquals(returnedData,observeData);

    }

    /**
     * retrieve list of notes
     * observe the list
     * return empty list
     */
    @Test
    void retrieveNotes_returnEmptyNoteList() throws Exception {
        // Arrange
        List<Note> returnedData = new ArrayList<>();
        LiveDataTestUtil<List<Note>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);

        // Act
        viewModel.getNotes();
        List<Note> observeData = listLiveDataTestUtil.getValue(viewModel.observeNotes());

        // Assert
        Assertions.assertEquals(returnedData,observeData);
    }

    /**
     * delete note
     * observe Resource.Success
     * return Resource.Success
     */
    @Test
    void deleteNote_observeResourceSuccess() throws Exception {
        // Arrange
        Note deletedNote = new Note (TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.success(1,DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        // Act
        Resource<Integer> observedValue = liveDataTestUtil.getValue(viewModel.deleteNote(deletedNote));

        // Assert
        Assertions.assertEquals(returnedData,observedValue);

    }

    /**
     * delete note
     * observe Resource.error
     * return Resource.error
     */
    @Test
    void deleteNote_observeResourceError() throws Exception {
        // Arrange
        Note deletedNote = new Note (TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.error(null,DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        // Act
        Resource<Integer> observedValue = liveDataTestUtil.getValue(viewModel.deleteNote(deletedNote));

        // Assert
        Assertions.assertEquals(returnedData,observedValue);

    }


}