package com.kdg.ui2animeproject.Data

import com.kdg.ui2animeproject.R
import com.kdg.ui2animeproject.model.AnimeSeries
import com.kdg.ui2animeproject.model.Character

object AnimeDataSource {
    val animeSeriesList = mutableListOf(
        AnimeSeries(
            1,
            "Naruto",
            "2002-10-03",
            "Action",
            "Pierrot",
            8.2,
            true,
            R.drawable.naruto
        ),
        AnimeSeries(
            2,
            "Attack on Titan",
            "2013-04-07",
            "Action",
            "Wit Studio",
            8.8,
            true,
            R.drawable.attackontitan
        ),
        AnimeSeries(
            3,
            "My Hero Academia",
            "2016-04-03",
            "Superhero",
            "Bones",
            8.5,
            false,
            R.drawable.bokuhero
        )

    )

    val charactersList = mutableListOf(
        Character(
            1, "Naruto Uzumaki", "Protagonist", 95.5, "Rasengan", 1
        ),
        Character(
            2, "Sasuke Uchiha", "Rival", 95.2, "Sharingan", 1
        ),
        Character(
            3, "Eren Yeager", "Protagonist", 92.3, "Titan Transformation", 2
        ),
        Character(
            4, "Izuku Midoriya", "Protagonist", 88.1, "One For All", 3
        )
    )
}