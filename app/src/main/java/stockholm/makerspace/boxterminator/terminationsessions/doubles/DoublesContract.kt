package stockholm.makerspace.boxterminator.terminationsessions.doubles

import stockholm.makerspace.boxterminator.models.Box
import stockholm.makerspace.boxterminator.models.DoubleBox


interface DoublesContract {

    interface View {
        fun showDoubles(doublesList: MutableList<DoubleBox>)
        fun showDoublesList(doublesList: MutableList<Box>)
    }

    interface Presenter {
        fun getDoubles()
    }
}