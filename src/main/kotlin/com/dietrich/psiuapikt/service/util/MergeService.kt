package com.dietrich.psiuapikt.service.util

import org.springframework.stereotype.Service
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

@Service
class MergeService {
    fun merge(from: Any, to: Any) {
        val fromKC = from::class
        val toKC = to::class

        val toMap = toKC.memberProperties.associateBy(KProperty<*>::name)

        fromKC.memberProperties.forEach {fromProperty ->
            toMap[fromProperty.name].takeIf {
                it is KMutableProperty<*>
            }?.let {
                it as KMutableProperty<*>
            }?.let {
                val fromValue = fromProperty.getter.call(from)
                fromValue?.let {fv ->
                    it.setter.call(to, fv)
                }
            }
        }
    }
}