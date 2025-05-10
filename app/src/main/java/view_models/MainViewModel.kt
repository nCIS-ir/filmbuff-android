package view_models

import activities.MainActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import enums.Direction
import enums.Sort

class MainViewModel : ViewModel() {
    val direction = MutableLiveData<Direction>(Direction.DESCENDING)
    val sort = MutableLiveData<Sort>(Sort.RELEASE)
    val shouldReload = MutableLiveData<Boolean>(false)
    val mode = MutableLiveData<MainActivity.Mode>(MainActivity.Mode.MOVIE)

    fun setDirection(direction: Direction) {
        this.direction.value = direction
    }

    fun setSort(sort: Sort) {
        this.sort.value = sort
    }

    fun setShouldReload(shouldReload: Boolean) {
        this.shouldReload.value = shouldReload
    }

    fun setMode(mode : MainActivity.Mode){
        this.mode.value = mode
    }
}