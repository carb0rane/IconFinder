package com.kode.iconfinder.IconFinder.di

import com.kode.iconfinder.MainActivity
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = [RetrofitModule::class])
interface Component {

fun inject(mainActivity: MainActivity)
}