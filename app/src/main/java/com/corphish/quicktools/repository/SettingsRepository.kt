package com.corphish.quicktools.repository

/**
 * Repository for user-configurable app settings persisted via SharedPreferences.
 */
interface SettingsRepository {
    fun getPrependCountryCodeEnabled(): Boolean

    fun setPrependCountryCodeEnabled(enabled: Boolean)

    fun getPrependCountryCode(): String?

    fun setPrependCountryCode(code: String)

    fun getDecimalPoints(): Int

    fun setDecimalPoints(points: Int)

    fun getEvaluateResultMode(): Int

    fun setEvaluateResultMode(mode: Int)

    fun getOnboardingDone(): Boolean

    fun setOnboardingDone(done: Boolean)
}
