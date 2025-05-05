package activities

import android.os.Bundle
import android.view.View
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

        b.tvUsername.text = buildString {
            append("@")
            append(App.USER.username)
        }

        b.tvEmail.text = App.USER.email

        if (App.USER.subscription > 0) {
            b.tvNone.visibility = View.GONE
            b.tvRemaining.text = buildString {
                append(App.USER.subscription)
                append(" ")
                append(getString(if (App.USER.subscription > 1) R.string.days else R.string.day))
            }
        } else {
            b.tvRemaining.visibility = View.GONE
        }

        b.tvCoins.text = App.USER.coins.toString()

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