package activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import fragments.AuthLoginFragment
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.databinding.ActivityAuthBinding


class AuthActivity : ActivityEnhanced() {
    private lateinit var b: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(b.root)

        showFragment(AuthLoginFragment())
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(b.fragment.id, fragment)
            .commit()
    }
}