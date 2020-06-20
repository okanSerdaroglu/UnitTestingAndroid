package com.example.unittestingandroid.di;

import static com.example.unittestingandroid.persistence.NoteDatabase.DATABASE_NAME;

import android.app.Application;

import androidx.room.Room;

import com.example.unittestingandroid.persistence.NoteDao;
import com.example.unittestingandroid.persistence.NoteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase) {
        return noteDatabase.getNoteDao();
    }

}
