package activities

import android.annotation.SuppressLint
import android.os.Bundle
import custom_views.MyButton
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : ActivityEnhanced() {
    private lateinit var b: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)
    }
}