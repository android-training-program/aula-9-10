package pt.atp.bobi.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import pt.atp.bobi.INVALID_ID

data class About(
    val type: AboutType,
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val subtitle: Int = INVALID_ID,
    val website: String? = null
) {
    enum class AboutType {
        APP, HEADER, BODY
    }
}