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

        b.cvLanguage.setOnClickListener {
            b.language.vgRoot.visibility = View.VISIBLE
            b.cvLanguage.setCardBackgroundColor(getColor(R.color.black))
            b.ivLanguage.setColorFilter(getColor(R.color.orange_300))
            b.tvLanguage.setTextColor(getColor(R.color.orange_300))
            b.ivLanguageArrow.setColorFilter(getColor(R.color.orange_300))
            b.ivLanguageArrow.rotation = 180f
        }

        b.cvFavorites.setOnClickListener {
            b.favorites.vgRoot.visibility = View.VISIBLE
            b.cvFavorites.setCardBackgroundColor(getColor(R.color.black))
            b.ivFavorites.setColorFilter(getColor(R.color.orange_300))
            b.tvFavorites.setTextColor(getColor(R.color.orange_300))
            b.ivFavoriteArrow.setColorFilter(getColor(R.color.orange_300))
            b.ivFavoriteArrow.rotation = 180f
        }

        b.cvPurchase.setOnClickListener {
            b.purchases.vgRoot.visibility = View.VISIBLE
            b.cvPurchase.setCardBackgroundColor(getColor(R.color.black))
            b.ivPurchase.setColorFilter(getColor(R.color.orange_300))
            b.tvPurchase.setTextColor(getColor(R.color.orange_300))
            b.ivPurchaseArrow.setColorFilter(getColor(R.color.orange_300))
            b.ivPurchaseArrow.rotation = 180f
        }

        b.cvSubscriptions.setOnClickListener {
            b.subscriptions.vgRoot.visibility = View.VISIBLE
            b.cvSubscriptions.setCardBackgroundColor(getColor(R.color.black))
            b.ivSubscriptions.setColorFilter(getColor(R.color.orange_300))
            b.tvSubscriptions.setTextColor(getColor(R.color.orange_300))
            b.ivSubscriptionsArrow.setColorFilter(getColor(R.color.orange_300))
            b.ivSubscriptionsArrow.rotation = 180f
        }

        b.cvPassword.setOnClickListener {
            b.password.vgRoot.visibility = View.VISIBLE
            b.cvPassword.setCardBackgroundColor(getColor(R.color.black))
            b.ivPassword.setColorFilter(getColor(R.color.orange_300))
            b.tvPassword.setTextColor(getColor(R.color.orange_300))
            b.ivPasswordArrow.setColorFilter(getColor(R.color.orange_300))
            b.ivPasswordArrow.rotation = 180f
        }
    }
}
