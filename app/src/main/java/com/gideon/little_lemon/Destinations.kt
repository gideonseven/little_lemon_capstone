package com.gideon.little_lemon

interface Destinations {
    val route: String
}

object Onboarding: Destinations {
    override val route = "Onboarding"
}


object Home: Destinations{
    override val route = "Home"
}

object Menu: Destinations{
    override val route = "Menu"
}