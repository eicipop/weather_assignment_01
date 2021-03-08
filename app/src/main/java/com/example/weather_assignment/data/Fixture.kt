package com.example.weather_assignment.data

object Fixture {
    val locations: List<Location> = listOf(
        Location(
            "Seoul",
            "City",
            1132599,
            "37.557121,126.977379"
        ),
        Location(
            "San Jose",
            "City",
            2488042,
            "37.338581,-121.885567"
        ),
        Location(
            "Seattle",
            "City",
            2490383,
            "47.603561,-122.329437"
        ),
        Location(
            "Brussels",
            "City",
            968019,
            "50.848381,4.349680"
        ),
        Location(
            "Boise",
            "City",
            2366355,
            "43.606979,-116.193413"
        ),
        Location(
            "Toulouse",
            "City",
            628886,
            "43.605728,1.448690"
        ),
        Location(
            "Marseille",
            "City",
            610264,
            "43.293701,5.372470"
        ),
        Location(
            "Sendai",
            "City",
            1118129,
            "38.314362,140.758194"
        ),
        Location(
            "Essen",
            "City",
            648820,
            "51.451809,7.0106"
        ),
        Location(
            "Southend-on-Sea",
            "City",
            35375,
            "51.548328,0.706400"
        ),
        Location(
            "Swansea",
            "City",
            36758,
            "51.623150,-3.940930"
        ),
        Location(
            "Düsseldorf",
            "City",
            646099,
            "51.215630,6.776040"
        ),
    )

    private val consolidateWeather: List<ConsolidatedWeather> = with(mutableListOf<ConsolidatedWeather>()){
        add(
            ConsolidatedWeather(
                "Heavy Rain",
                IconAbbreviation.hr,
                32.114999999999995,
                61,
                "2021-03-03")
        )

        add(ConsolidatedWeather(
            "Showers",
            IconAbbreviation.s,
            32.61,
            59,
            "2021-03-03"))

        add(ConsolidatedWeather(
            "Heavy Rain",
            IconAbbreviation.hr,
            31.46,
            65,
            "2021-03-03"))
        this
    }
    val seoulWeather = Weather(
        "Seoul",
        104738,
        consolidateWeather
    )
    val seoulWeather2 = Weather(
        "Seoul",
        104739,
        consolidateWeather
    )
    val seoulWeather3 = Weather(
        "Seoul",
        104740,
        consolidateWeather
    )
    val seoulWeather4 = Weather(
        "Seoul",
        104741,
        consolidateWeather
    )
    val seoulWeather5 = Weather(
        "Seoul",
        104742,
        consolidateWeather
    )

    val weatherList:List<Weather> = listOf<Weather>(seoulWeather, seoulWeather2, seoulWeather3, seoulWeather4, seoulWeather5)
}