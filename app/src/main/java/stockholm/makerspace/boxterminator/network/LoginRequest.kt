package stockholm.makerspace.boxterminator.network

data class LoginRequest(
    val grant_type: String = "password",
    val username : String,
    val password : String
)
