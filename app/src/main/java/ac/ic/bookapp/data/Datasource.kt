package ac.ic.bookapp.data

abstract class Datasource<T>(
    private val apiClass: Class<T>
) {
    protected val service: T by lazy {
        Backend.production.create(apiClass)
    }
}