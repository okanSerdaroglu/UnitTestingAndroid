package com.example.unittestingandroid.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.unittestingandroid.ui.note.NoteViewModel;
import com.example.unittestingandroid.ui.notelist.NoteListViewModel;
import com.example.unittestingandroid.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory
    bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel.class)
    public abstract ViewModel bindNoteViewModel(NoteViewModel noteViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel.class)
    public abstract ViewModel bindNoteListViewModel(NoteListViewModel noteViewModel);

}
