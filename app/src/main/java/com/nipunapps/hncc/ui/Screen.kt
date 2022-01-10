package com.nipunapps.hncc.ui

sealed class Screen(val title : String,val route : String){
    object HomeScreen : Screen("HOME","home")
    object AboutScreen : Screen("ABOUT","about")
    object TeamScreen : Screen("TEAM","team")
    object JoinUsScreen : Screen("Join Us","join_us")
}
