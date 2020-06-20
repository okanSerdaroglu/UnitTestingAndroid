package com.example.unittestingandroid;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.unittestingandroid.models.Note;
import com.example.unittestingandroid.persistence.NoteDao;
import com.example.unittestingandroid.persistence.NoteDatabase;
import com.example.unittestingandroid.util.TestUtil;

import org.junit.After;
import org.junit.Before;

public abstract class NoteDatabaseTest {

    // system under test
    private NoteDatabase noteDatabase;

    public NoteDao getNoteDao() {
        return noteDatabase.getNoteDao();
    }

    @Before
    public void init() {
        // mock database for testing
        noteDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).build();
    }

    @After
    public void finish() {
        noteDatabase.close();
    }


}
