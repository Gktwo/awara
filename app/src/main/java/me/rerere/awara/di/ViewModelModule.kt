package me.rerere.awara.di

import me.rerere.awara.ui.page.index.IndexVM
import me.rerere.awara.ui.page.login.LoginVM
import me.rerere.awara.ui.stores.UserStoreVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginVM)
    viewModelOf(::IndexVM)
    viewModelOf(::UserStoreVM)
}