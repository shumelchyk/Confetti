@file:OptIn(ExperimentalSettingsApi::class, ApolloExperimental::class)

package dev.johnoreilly.confetti.di

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import dev.johnoreilly.confetti.auth.Authentication
import dev.johnoreilly.confetti.utils.DateService
import dev.johnoreilly.confetti.utils.JvmDateService
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.util.prefs.Preferences

@OptIn(ExperimentalSettingsApi::class)
actual fun platformModule() = module {
    single<Authentication> { Authentication.Disabled }
    single<ObservableSettings> { PreferencesSettings(Preferences.userRoot()) }
    single { get<ObservableSettings>().toFlowSettings() }
    singleOf(::JvmDateService) { bind<DateService>() }
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .build()
    }
    single<FetchPolicy> {
        FetchPolicy.CacheAndNetwork
    }
}

actual fun getDatabaseName(conference: String, uid: String?) = "jdbc:sqlite:$conference$uid.db"
