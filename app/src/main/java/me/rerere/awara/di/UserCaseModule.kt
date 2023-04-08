package me.rerere.awara.di

import me.rerere.awara.domain.GetVideoInfoCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val userCaseModule = module {
    factoryOf(::GetVideoInfoCase)
}