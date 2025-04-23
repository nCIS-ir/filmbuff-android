package retrofit.models

data class Info(
    val activities: List<Activity>,
    val countries: List<Country>,
    val genres: List<Genre>,
    val languages: List<Language>,
    val packs: List<Pack>,
    val plans: List<Plan>,
    val qualities: List<Quality>,
    val roles: List<Role>,
)
