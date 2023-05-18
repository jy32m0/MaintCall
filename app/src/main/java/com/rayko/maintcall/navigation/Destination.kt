package com.rayko.maintcall.navigation

sealed class Destination(val route: String) {

    object HomeScreen: Destination("home")

    object DBCScreen: Destination("dbcs")

    object MiscEquipScreen: Destination("misc/{equipDestName}") {
        fun routeToMisc(equipRtName: String) = "misc/$equipRtName"
    }

    object LogScreen: Destination("log/{equipDestName}, {equipDestNum}") {
        fun routeToLog(equipRtName: String, equipRtNum: String)
                                = "log/$equipRtName, $equipRtNum"
    }

    object DetailScreen: Destination("detail/{logID}") {
        fun routeToDetail(logID: Long)
                                = "detail/${logID.toString()}"
    }
}
