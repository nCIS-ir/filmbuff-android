package activities

import android.annotation.SuppressLint
import android.os.Bundle
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.databinding.ActivityAuthBinding


@SuppressLint("CustomSplashScreen")
class AuthActivity : ActivityEnhanced() {
    private lateinit var b: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(b.root)
    }
}