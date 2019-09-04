package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filter { it.name !in (this.trips.map {trip -> trip.driver.name })}.toSet()
        // Solution
        // allDrivers.minus(trips.map { it.driver })

/*ß
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter { this.trips.filter { trip -> it in trip.passengers }.size >= minTrips}.toSet()
        /* Solution 1
        trips
            .flatMap { Trip::passengers }
            .groupBy { passenger -> passenger }
            .filter { group -> group.value.size >= minTrips }
            .keys
        */
        /* Solution 2
        allPassengers
            .filter { p ->
                trips.count {p in it.passengers}
                } >= minTrips
            }
            .toSet()
        */

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter { this.trips.filter { trip -> it in trip.passengers && trip.driver == driver }.size > 1 }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter {
            val (discount, nonDiscount) = this.trips.filter { trip ->  it in trip.passengers}.partition { trip -> trip.discount ?: 0.0 > 0.0 }
            discount.size > nonDiscount.size
        }.toSet()
        /* Solution
        val (tripsWithDiscount, tripsWithoutDiscount) = trips.partition {it.discount != null}
        return allPassengers
            .filter { passenger ->
                tripsWithDiscount.count {passenger in it.passengers} >
                tripsWithoutDiscount.count {passenger in it.passengers}}
            .toSet()
        */

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    var start = this.trips.groupBy { it.duration / 10 }.maxBy { entry -> entry.value.size }?.key
    return if (start != null) IntRange(start * 10, start * 10 + 9) else null
    /* Solution
    return trips
        .groupBy {
            val start = it.duration / 10 * 10
            val end = start + 9
            start..end
        }
        .toList()
        .maxBy {(_, group) -> group.size}
        ?.key
    */
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val top20Driver = (this.allDrivers.size * 0.2).toInt()
    val driverMap = this.trips.groupBy { it.driver }.mapValues { entry -> entry.value.sumByDouble { trip -> trip.cost } }
    val top20DriverSum = driverMap.toList().sortedByDescending { (key, value)  -> value}.take(top20Driver).sumByDouble { pair -> pair.second
    }
    val totalSum = this.trips.sumByDouble { it.cost }
    return if (this.trips.isEmpty()) false else top20DriverSum >= totalSum * 0.8
}