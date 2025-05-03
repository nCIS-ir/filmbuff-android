package activities

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import dialogs.ConfirmDialog
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth

class ProfileActivity : ActivityEnhanced() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.ivBack.setOnClickListener { finish() }

        b.cvLogout.setOnClickListener {
            ConfirmDialog(App.ACTIVITY, getString(R.string.confirm_exit), getString(R.string.exit_message)) {
                lifecycleScope.launch {
                    Auth.logout(
                        {
                            Hawk.deleteAll()
                            App.DB.clearAllTables()
                            App.ACTIVITY.runActivity(SplashActivity::class.java, shouldFinish = true)
                        }
                    )
                }
            }.show()
        }
    }
}