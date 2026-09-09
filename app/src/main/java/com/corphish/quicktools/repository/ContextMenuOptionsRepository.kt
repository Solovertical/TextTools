package com.corphish.quicktools.repository

/**
 * Repository for the context menu options.
 */
interface ContextMenuOptionsRepository {

    /**
     * Gets the current app mode.
     */
    fun getCurrentAppMode(): AppMode

    /**
     * Sets the current app mode.
     * Suspends because this enables/disables activity-alias components via [android.content.pm.PackageManager],
     * which performs blocking IPC and should not run on the main thread.
     */
    suspend fun setCurrentAppMode(mode: AppMode)

    /**
     * Returns the currently enabled feature set.
     */
    fun getCurrentlyEnabledFeatures(): List<FeatureIds>

    /**
     * Enables or disables a feature.
     * Suspends for the same reason as [setCurrentAppMode].
     */
    suspend fun enableOrDisableFeature(feature: FeatureIds, enabled: Boolean)
}

enum class AppMode {
    SINGLE,
    MULTI
}

enum class FeatureIds {
    EVAL,
    WHATSAPP,
    TRANSFORM,
    TEXT_COUNT,
    SAVE_TEXT,
    FIND_AND_REPLACE,
    NUMBER_ANALYSIS,
    TEXT_TEMPLATE,
    TEXT_ACTION
}