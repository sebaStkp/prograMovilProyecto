package com.ucb.perritos.features.core

import io.github.jan.supabase.postgrest.Postgrest


import io.github.jan.supabase.auth.Auth

import com.ucb.perritos.BuildConfig

import io.github.jan.supabase.createSupabaseClient

val supabase = createSupabaseClient(
    supabaseUrl = "https://efxdadwzzvsuvtefmlbm.supabase.co",
    supabaseKey = BuildConfig.SUPABASE_KEY
) {
    install(Postgrest)
    install(Auth)
}