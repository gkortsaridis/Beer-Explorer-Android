package gr.gkortsaridis.beerexplorer.data.model

import com.google.gson.annotations.SerializedName

data class Beer(
    val id: Long,
    val name: String,
    val tagline: String,
    @SerializedName("first_brewed")
    val firstBrewed: String,
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val abv: Double,
    val ibu: Double?,
    @SerializedName("target_fg")
    val targetFg: Double,
    @SerializedName("target_og")
    val targetOg: Double,
    val ebc: Double?,
    val srm: Double?,
    val ph: Double?,
    @SerializedName("attenuation_level")
    val attenuationLevel: Double,
    val volume: Volume,
    @SerializedName("boil_volume")
    val boilVolume: BoilVolume,
    val method: Method,
    val ingredients: Ingredients,
    @SerializedName("food_pairing")
    val foodPairing: List<String>,
    @SerializedName("brewers_tips")
    val brewersTips: String,
    @SerializedName("contributed_by")
    val contributedBy: String,
)

data class Volume(
    val value: Double,
    val unit: String,
)

data class BoilVolume(
    val value: Double,
    val unit: String,
)

data class Method(
    @SerializedName("mash_temp")
    val mashTemp: List<MashTemp>,
    val fermentation: Fermentation,
    val twist: String?,
)

data class MashTemp(
    val temp: Temp,
    val duration: Long?,
)

data class Temp(
    val value: Double,
    val unit: String,
)

data class Fermentation(
    val temp: Temp,
)

data class Ingredients(
    val malt: List<Malt>,
    val hops: List<Hop>,
    val yeast: String,
)

data class Malt(
    val name: String,
    val amount: Amount,
)

data class Amount(
    val value: Double,
    val unit: String,
)

data class Hop(
    val name: String,
    val amount: Amount,
    val add: String,
    val attribute: String,
)