package activities

import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.databinding.ActivityMainBinding

class MainActivity : ActivityEnhanced() {
    private lateinit var b: ActivityMainBinding

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
    }
}