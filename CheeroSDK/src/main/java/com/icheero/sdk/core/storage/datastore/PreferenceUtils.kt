package com.icheero.sdk.core.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

@Suppress("UNCHECKED_CAST")
class PreferenceUtils {

    companion object {
        private const val PREFERENCES_NAME = "cheero_prefs"

        // preferencesDataStore 保证 datastore 只有一个实例
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)
        private lateinit var dataStore: DataStore<Preferences>

        val instance: PreferenceUtils by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PreferenceUtils()
        }

        fun init(context: Context) {
            dataStore = context.applicationContext.dataStore
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    fun <T> getDataValue(key: String, default: T): T {
        return when (default) {
            is Boolean -> getBooleanValue(key, default)
            is Int -> getIntValue(key, default)
            is Long -> getLongValue(key, default)
            is Float -> getFloatValue(key, default)
            is Double -> getDoubleValue(key, default)
            is String -> getStringValue(key, default)
            else -> throw IllegalArgumentException("This type ${default!!::class} can not be saved into DataStore")
        } as T
    }

    fun <T> getDataFlow(key: String, default: T): Flow<T> {
        return when (default) {
            is Boolean -> getBooleanFlow(key, default)
            is Int -> getIntFlow(key, default)
            is Long -> getLongFlow(key, default)
            is Float -> getFloatFlow(key, default)
            is Double -> getDoubleFlow(key, default)
            is String -> getStringFlow(key, default)
            is Set<*> -> getStringSetFlow(key, default)
            else -> throw IllegalArgumentException("This type ${default!!::class} can not be saved into DataStore")
        } as Flow<T>
    }

    /*
    suspend fun <U> putData(key: String, value: U) {
        when (value) {
            is Long -> saveLongData(key, value)
            is String -> saveStringData(key, value)
            is Int -> saveIntData(key, value)
            is Boolean -> saveBooleanData(key, value)
            is Float -> saveFloatData(key, value)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
    }

    fun <U> putSyncData(key: String, value: U) {
        when (value) {
            is Long -> saveSyncLongData(key, value)
            is String -> saveSyncStringData(key, value)
            is Int -> saveSyncIntData(key, value)
            is Boolean -> saveSyncBooleanData(key, value)
            is Float -> saveSyncFloatData(key, value)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
    }
    */

    // region get value
    fun getBooleanValue(key: String, default: Boolean = false): Boolean {
        var value = default
        runBlocking {
            dataStore.data.first {
                value = it[booleanPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun getIntValue(key: String, default: Int): Int {
        var value = default
        runBlocking {
            dataStore.data.first {
                value = it[intPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun getLongValue(key: String, default: Long): Long {
        var value = default
        runBlocking {
            dataStore.data.first {
                value = it[longPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun getFloatValue(key: String, default: Float): Float {
        var value = default
        runBlocking {
            dataStore.data.first {
                value = it[floatPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun getDoubleValue(key: String, default: Double): Double {
        var value = default
        runBlocking {
            dataStore.data.first {
                value = it[doublePreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun getStringValue(key: String, default: String = ""): String {
        var value = default
        runBlocking {
            dataStore.data.first {
                value = it[stringPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }
    // endregion

    // region get flow
    fun getBooleanFlow(key: String, default: Boolean = false): Flow<Boolean> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else throw it
        }.map { it[booleanPreferencesKey(key)] ?: default }
    }

    fun getIntFlow(key: String, default: Int = -1): Flow<Int> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else throw it
        }.map { it[intPreferencesKey(key)] ?: default }
    }

    fun getLongFlow(key: String, default: Long = -1L): Flow<Long> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else throw it
        }.map { it[longPreferencesKey(key)] ?: default }
    }

    fun getFloatFlow(key: String, default: Float = -1f): Flow<Float> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else throw it
        }.map { it[floatPreferencesKey(key)] ?: default }
    }

    fun getDoubleFlow(key: String, default: Double = -1.0): Flow<Double> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else throw it
        }.map { it[doublePreferencesKey(key)] ?: default }
    }

    fun getStringFlow(key: String, default: String = ""): Flow<String> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { it[stringPreferencesKey(key)] ?: default }
    }

    fun getStringSetFlow(key: String, default: Set<*>): Flow<Set<String>> {
        default as? Set<String>
                ?: throw IllegalArgumentException("This type ${default::class} can not be saved into DataStore")
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { it[stringSetPreferencesKey(key)] ?: default }
    }
    // endregion

    suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[booleanPreferencesKey(key)] = value
        }
    }

    fun putBooleanSync(key: String, value: Boolean) {
        runBlocking { putBoolean(key, value) }
    }
}