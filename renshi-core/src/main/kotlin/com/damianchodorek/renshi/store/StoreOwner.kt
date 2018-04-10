package com.damianchodorek.renshi.store

/**
 * Interface for all classes that use [Store].
 */
interface StoreOwner {

    val store: Store<*>?
}