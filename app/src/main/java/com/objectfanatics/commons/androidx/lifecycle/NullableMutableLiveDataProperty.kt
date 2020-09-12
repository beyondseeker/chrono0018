package com.objectfanatics.commons.androidx.lifecycle

import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// FIXME: これは of-commons 側。
// FIXME: interceptor の部分 operator fun provideDelegate とかで対応できるのかな？要確認。
open class NullableMutableLiveDataProperty<T>(private val delegatee: MutableLiveData<T?>, private val setterInterceptor: (T?) -> T? = { value -> value }) : ReadWriteProperty<Any?, T?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? =
        delegatee.value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        delegatee.postValue(setterInterceptor(value))
    }
}