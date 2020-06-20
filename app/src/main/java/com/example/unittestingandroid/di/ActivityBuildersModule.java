package com.example.unittestingandroid.di;

import com.example.unittestingandroid.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributeNoteListActivity();

}
