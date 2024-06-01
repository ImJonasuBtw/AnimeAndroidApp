package com.kdg.ui2animeproject.model
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val role: String,
    val powerLevel: Double,
    val specialAbility: String,
    val animeSerieId: Int
)


