package com.kis.di

import com.kis.service.ExtractService
import com.kis.services.RefererService
import org.koin.dsl.module

val mediaModule = module {
    single { ExtractService() }
    single { RefererService() }
}