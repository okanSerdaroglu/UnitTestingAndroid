package com.example.unittestingandroid.di;

import com.example.unittestingandroid.ui.note.NoteActivity;
import com.example.unittestingandroid.ui.notelist.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributeNoteListActivity();

    @ContributesAndroidInjector
    abstract NoteActivity contributeNoteActivity();

}
