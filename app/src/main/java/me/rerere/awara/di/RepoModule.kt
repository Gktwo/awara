package me.rerere.awara.di

import me.rerere.awara.data.repo.UserRepo
import org.koin.dsl.module

val repoModule = module {
    single {
        UserRepo(get())
    }
}