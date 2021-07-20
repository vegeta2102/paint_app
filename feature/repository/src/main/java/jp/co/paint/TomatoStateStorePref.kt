package jp.co.paint

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonNullablePref
import jp.co.paint.model.TomatoState
import javax.inject.Inject

/**
 * Created by vegeta
 */
class TomatoStateStorePref @Inject constructor() : KotprefModel() {
    override val commitAllPropertiesByDefault: Boolean = true
    override val kotprefName: String = "tomato_pref"

    var tomatoState: TomatoState? by gsonNullablePref(default = null)
}