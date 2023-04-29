package me.rerere.awara.di

import me.rerere.awara.ui.page.favorites.FavoritesVM
import me.rerere.awara.ui.page.history.HistoryVM
import me.rerere.awara.ui.page.image.ImageVM
import me.rerere.awara.ui.page.index.IndexVM
import me.rerere.awara.ui.page.login.LoginVM
import me.rerere.awara.ui.page.playlist.PlaylistDetailVM
import me.rerere.awara.ui.page.playlist.PlaylistsVM
import me.rerere.awara.ui.page.user.UserVM
import me.rerere.awara.ui.page.video.VideoVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginVM)
    viewModelOf(::IndexVM)
    viewModelOf(::VideoVM)
    viewModelOf(::UserVM)
    viewModelOf(::ImageVM)
    viewModelOf(::HistoryVM)
    viewModelOf(::PlaylistDetailVM)
    viewModelOf(::PlaylistsVM)
    viewModelOf(::FavoritesVM)
}