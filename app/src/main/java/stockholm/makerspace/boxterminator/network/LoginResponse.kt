package stockholm.makerspace.boxterminator.network

data class LoginResponse(
    val access_token: String,
    val expires: String,
    val status: String
)
