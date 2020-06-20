package com.example.unittestingandroid.di;

import androidx.lifecycle.ViewModelProvider;

import com.example.unittestingandroid.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory
    bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

}
