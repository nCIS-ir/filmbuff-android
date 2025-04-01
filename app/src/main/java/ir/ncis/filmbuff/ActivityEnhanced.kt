package ir.ncis.filmbuff

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class ActivityEnhanced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.INFLATER = layoutInflater
        App.ACTIVITY = this
    }

    override fun onResume() {
        super.onResume()
        App.ACTIVITY = this
    }

    fun runActivity(targetActivity: Class<*>, bundle: Bundle? = null, shouldFinishCurrentActivity: Boolean = false) {
        val intent = Intent(this, targetActivity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (shouldFinishCurrentActivity) {
            finish()
        }
    }
}