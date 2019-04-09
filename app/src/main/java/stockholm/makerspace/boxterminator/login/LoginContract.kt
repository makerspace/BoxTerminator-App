package stockholm.makerspace.boxterminator.login


interface LoginContract {

    interface Presenter{
        fun login(username: String, pass: String)
    }

    interface View{
        fun showError(message: String?)
        fun allowAccess()
    }
}