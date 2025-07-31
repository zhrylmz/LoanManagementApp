package com.loanmanagementapp.contract.json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import kotlin.reflect.KClass

class JsonParserManager @Inject constructor(

) : JsonParser {

    override fun <T : Any> fromJsonToArrayList(
        json: String?,
        parameterClasses: KClass<T>
    ): ArrayList<T>? {
        val type = TypeToken.getParameterized(
            ArrayList::class.java,
            parameterClasses.java
        ).type

        return try {
            Gson().fromJson<ArrayList<T>>(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
