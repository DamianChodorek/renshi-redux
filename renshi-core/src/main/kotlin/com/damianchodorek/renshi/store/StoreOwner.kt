package com.damianchodorek.renshi.store

/**
 * Interface for all classes that use [Store].
 * @author Damian Chodorek
 */
interface StoreOwner {

    val store: Store<*>?
}