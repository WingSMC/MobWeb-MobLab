package hu.bme.aut.workplaceapp.data

object DataManager {
    private const val HOLIDAY_MAX_VALUE = 20
    private const val HOLIDAY_DEFAULT_VALUE = 15

    var holidays = HOLIDAY_DEFAULT_VALUE
    val remainingHolidays get() = HOLIDAY_MAX_VALUE - holidays
    val person = Person(
        "Test User", "testuser@domain.com",
        "1234 Test, Random Street 1.",
        "123456AB",
        "123456789",
        "1234567890",
        "0123456789"
    )
    val taxes = listOf(
        Tax("SZJA", 0.15),
        Tax("TB", 0.185),
        Tax("SzocHo", 0.155),
        Tax("SzakHo", 0.015)
    )

    val grossPayment = listOf(
        765915.0, 773943.0, 822089.0,
        949226.0, 954856.0, 922231.0,
        857350.0, 850842.0, 998409.0,
        820122.0, 909875.0, 820978.0
    )
}
