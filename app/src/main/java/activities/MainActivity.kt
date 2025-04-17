package activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import enums.Direction
import enums.Sort
import fragments.FragmentMainHome
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.databinding.ActivityMainBinding

class MainActivity : ActivityEnhanced() {
    companion object {
        var sort = Sort.POPULARITY
        var direction = Direction.DESCENDING
    }

    private lateinit var b: ActivityMainBinding
    var mode = Mode.MOVIE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        showFragment(FragmentMainHome())
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(b.fragment.id, fragment)
            .commit()
    }

    enum class Mode {
        MOVIE,
        SERIE,
    }
}