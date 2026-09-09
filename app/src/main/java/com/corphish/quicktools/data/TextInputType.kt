package com.corphish.quicktools.data

/**
 * Platform-agnostic keyboard/input type hint for a text field driven by a ViewModel.
 * Kept independent of any specific UI toolkit (e.g. Compose's `KeyboardType`) so that
 * ViewModels don't depend on the UI layer; the UI layer maps this to its own toolkit type.
 */
enum class TextInputType {
    TEXT,
    NUMBER
}
