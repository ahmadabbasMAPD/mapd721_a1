package com.abbas.mapd721_a1_ahmadabbas


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
// This is a class that handles data storage using preferences
class DataStore(private val context: Context) {
    companion object {
        // This is a property that creates a data store instance with the name "DATA"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("DATA")

        // These are the keys for storing and retrieving user data
        val usernameKey = stringPreferencesKey("USERNAME")
        val userEmailKey = stringPreferencesKey("EMAIL")
        val userIdKey = intPreferencesKey("USERID")
    }

    // This is a function that saves user data to the data store
    suspend fun saveUserData(userData: UserData) {
        context.dataStore.edit { preference ->
            // Set the values for each key using the user data object
            preference[usernameKey] = userData.user_name
            preference[userEmailKey] = userData.user_email
            preference[userIdKey] = userData.user_id
        }
    }

    // This is a function that clears user data from the data store
    suspend fun clearUserData() {
        context.dataStore.edit { preference ->
            // Set the values for each key to empty or default values
            preference[usernameKey] = ""
            preference[userEmailKey] = ""
            preference[userIdKey] = -1
        }
    }

    // These are properties that return flows of user data from the data store
    val getUsername: Flow<String?> = context.dataStore.data.map { preference ->
        // Get the value for the username key or return an empty string
        preference[usernameKey] ?: ""
    }

    val getUserEmail: Flow<String?> = context.dataStore.data.map { preference ->
        // Get the value for the email key or return an empty string
        preference[userEmailKey] ?: ""
    }

    val getUserId: Flow<Int?> = context.dataStore.data.map { preference->
        // Get the value for the user id key or return -1
        preference[userIdKey] ?: -1
    }
}
