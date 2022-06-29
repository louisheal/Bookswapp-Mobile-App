package ac.ic.bookapp.data

enum class Backend(public val url: String) {
    STAGING("http://drp19-staging.herokuapp.com/"),
    PRODUCTION("http://drp19.herokuapp.com/");
}