package ac.ic.bookapp.model

abstract class Datasource<T>(
    private val apiClass: Class<T>
) {
    protected val service: T by lazy {
        Backend.staging.create(apiClass)
    }
}