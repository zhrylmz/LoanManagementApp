package com.loanmanagementapp.contract.json

import kotlin.reflect.KClass

interface JsonParser {
    fun <T : Any> fromJsonToArrayList(json: String?, parameterClasses: KClass<T>): ArrayList<T>?

}
