package com.damianchodorek.renshi.controller

interface ControllerOwner {

    fun createController(): Controller?
}