package com.skoorc.atvolunteeraid.util

import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.model.User

class MockUtil {

    fun randomName(): String {return arrayOf("JMC", "Shelly", "BikerBae","barrelBowls", "sharknado", "yogiBear").random()}
    fun randomIssueType(): String {return arrayOf("Trash", "Trail Blocked", "Poop", "Missing Marker", "Scary Bears").random()}
    fun randomStatus(): String {return arrayOf("Resolved", "Unresolved", "Stale", "Unknown").random()}
    fun randomDay():String {return  (1..31).random().toString()}
    fun randomMonth():String {return  (1..12).random().toString()}
    fun randomYear():String {return  (1990..2023).random().toString()}
    fun randomDate(): String {return "${randomYear()}/${randomMonth()}/${randomDay()}"}
    fun randomEmail():String {return  randomName() + "@gmail.com"}
    fun randomState():String {return  arrayOf("CA", "MD", "NC", "OR", "SC", "DE", "RI", "KA", "AL").random()}
    fun randomLatitude(): Double {return getRandom(37.00000593, 37.960556593) }
    fun randomLongitude(): Double { return getRandom(-78.92579674, -78.00000007)}

    fun getRandom(min: Double, max: Double): Double {
        require(min < max) { "Invalid range [$min, $max]" }
        return min + Math.random() * (max - min)
    }

    fun getRandomLocationReport(status: String = randomStatus(), resolvedBy: String? = null, additionalInfo: String? = null, dateResolved: String? = null ): Location {
        return Location(
            latitude = randomLatitude().toString().substring(0..10),
            longitude = randomLongitude().toString().substring(0..11),
            date = randomDate(),
            type =  randomIssueType(),
            status = status,
            reportedBy =  randomName(),
            resolvedBy = resolvedBy,
            additionalInfo = additionalInfo,
            dateResolved = dateResolved,
        )
    }

    fun getListOfLocations(size: Int): List<Location> {
        val list = mutableListOf<Location>()
        for (i in 0..size ) {
            list.add(getRandomLocationReport())
        }
        return list
    }

    fun getRandomUser(): User {
        return User(
            randomEmail(),
            randomName(),
            randomDate(),
            false,
            randomState(),
            false
        )
    }
}