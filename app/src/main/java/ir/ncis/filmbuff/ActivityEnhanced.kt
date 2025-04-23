package ir.ncis.filmbuff

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import dialogs.ConfirmDialog

open class ActivityEnhanced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.INFLATER = layoutInflater
        App.ACTIVITY = this
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun onResume() {
        super.onResume()
        App.ACTIVITY = this
    }

    fun runActivity(targetActivity: Class<*>, bundle: Bundle? = null, shouldFinish: Boolean = false) {
        val intent = Intent(this, targetActivity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (shouldFinish) {
            finish()
        }
    }

    protected fun checkedExit() {
        ConfirmDialog(this, getString(R.string.confirm_exit), getString(R.string.exit_message)) { App.exit() }.show()
    }
}