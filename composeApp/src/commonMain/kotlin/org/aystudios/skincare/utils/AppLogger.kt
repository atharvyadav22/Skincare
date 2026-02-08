package org.aystudios.skincare.utils

import co.touchlab.kermit.Logger

object AppLogger {

    val network = Logger.withTag("NETWORK")
    val auth = Logger.withTag("AUTH")
    val products = Logger.withTag("PRODUCTS")
    val users = Logger.withTag("USERS")

    val cart = Logger.withTag("CART")

}