package gr.gkortsaridis.beerexplorer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable

@Parcelize
data class Volume(
    val value: Double,
    val unit: String,
) : Parcelable

@Parcelize
data class BoilVolume(
    val value: Double,
    val unit: String,
) : Parcelable

@Parcelize
data class Method(
    @SerializedName("mash_temp")
    val mashTemp: List<MashTemp>,
    val fermentation: Fermentation,
    val twist: String?,
) : Parcelable

@Parcelize
data class MashTemp(
    val temp: Temp,
    val duration: Long?,
) : Parcelable

@Parcelize
data class Temp(
    val value: Double,
    val unit: String,
) : Parcelable

@Parcelize
data class Fermentation(
    val temp: Temp,
) : Parcelable

/**
 * I generally i am not a huge fan of tweaking data classes with anything other POJOs,
 * but i couldn't resist the urge to put the formatters in here instead of the ViewModel,
 * since this would make the VM practically useless for the BeerDetailsActivity
 */
@Parcelize
data class Ingredients(
    val malt: List<Malt>,
    val hops: List<Hop>,
    val yeast: String,
) : Parcelable {
    val maltFormatted: String
        get() {
            val sb = StringBuilder()
            malt.forEach {
                sb.append(it.name)
                sb.append(" : ")
                sb.append(it.amount.value)
                sb.append(" ")
                sb.append(it.amount.unit)
                sb.append("\n")
            }
            return sb.toString()
        }

    val hopsFormatted: String
        get() {
            val sb = StringBuilder()
            hops.forEach {
                sb.append(it.name)
                sb.append(" : ")
                sb.append(it.amount.value)
                sb.append(" ")
                sb.append(it.amount.unit)
                sb.append("\n")
            }
            return sb.toString()
        }
}

@Parcelize
data class Malt(
    val name: String,
    val amount: Amount,
) : Parcelable

@Parcelize
data class Amount(
    val value: Double,
    val unit: String,
) : Parcelable

@Parcelize
data class Hop(
    val name: String,
    val amount: Amount,
    val add: String,
    val attribute: String,
) : Parcelable