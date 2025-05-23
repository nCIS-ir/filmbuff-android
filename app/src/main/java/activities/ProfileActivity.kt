package activities

import adapters.AdapterRecyclerMovie
import adapters.AdapterRecyclerPurchase
import adapters.AdapterRecyclerSerie
import adapters.AdapterRecyclerSubscription
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.hawk.Hawk
import dialogs.ConfirmDialog
import helpers.LocaleHelper
import helpers.ViewHelper
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth
import retrofit.calls.Movie
import retrofit.calls.Serie
import retrofit.calls.User
import view_models.ProfileViewModel

class ProfileActivity : ActivityEnhanced() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var b: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(b.root)

        observe()

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
            ConfirmDialog(this, getString(R.string.confirm_exit), getString(R.string.exit_message)) {
                lifecycleScope.launch {
                    Auth.logout(
                        {
                            Hawk.deleteAll()
                            App.DB.clearAllTables()
                            runActivity(SplashActivity::class.java, shouldFinish = true)
                        }
                    )
                }
            }.show()
        }

        b.cvLanguage.setOnClickListener { profileViewModel.toggleLanguage() }

        b.cvFavorites.setOnClickListener { profileViewModel.toggleFavorites() }

        b.cvPurchases.setOnClickListener { profileViewModel.togglePurchases() }

        b.cvSubscriptions.setOnClickListener { profileViewModel.toggleSubscriptions() }

        b.cvPassword.setOnClickListener { profileViewModel.togglePassword() }

        when (LocaleHelper.getCurrentLocale()) {
            "en" -> {
                b.language.cvEnglish.setCardBackgroundColor(getColor(R.color.black))
                b.language.tvEnglish.setTextColor(getColor(R.color.orange_300))
                b.language.ivSelectedEnglish.visibility = View.VISIBLE
                b.language.cvFarsi.setCardBackgroundColor(getColor(R.color.blue_dark_400))
                b.language.tvFarsi.setTextColor(getColor(R.color.white))
                b.language.ivSelectedFarsi.visibility = View.INVISIBLE
            }

            "fa" -> {
                b.language.cvEnglish.setCardBackgroundColor(getColor(R.color.blue_dark_400))
                b.language.tvEnglish.setTextColor(getColor(R.color.white))
                b.language.ivSelectedEnglish.visibility = View.INVISIBLE
                b.language.cvFarsi.setCardBackgroundColor(getColor(R.color.black))
                b.language.tvFarsi.setTextColor(getColor(R.color.orange_300))
                b.language.ivSelectedFarsi.visibility = View.VISIBLE
            }
        }

        b.language.cvEnglish.setOnClickListener { LocaleHelper.changeLocale(this, LocaleHelper.EN) }
        b.language.cvFarsi.setOnClickListener { LocaleHelper.changeLocale(this, LocaleHelper.FA) }

        b.password.btConfirm.setOnClickListener {
            val oldPassword = b.password.etOldPassword.editableText.toString()
            val newPassword = b.password.etNewPassword.editableText.toString()
            val repeatNewPassword = b.password.etRepeatNewPassword.editableText.toString()
            var error: String? = null
            if (oldPassword.isEmpty()) {
                error = getString(R.string.error_old_password)
            } else {
                if (newPassword.length < 8) {
                    error = getString(R.string.error_new_password)
                } else {
                    if (repeatNewPassword != newPassword) {
                        error = getString(R.string.error_repeat_password)
                    }
                }
            }
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    User.password(
                        oldPassword,
                        newPassword,
                        repeatNewPassword,
                        {
                            Toast.makeText(this@ProfileActivity, "Password has been changed successfully.", Toast.LENGTH_SHORT).show()
                            b.password.etOldPassword.text = null
                            b.password.etNewPassword.text = null
                            b.password.etRepeatNewPassword.text = null
                        },
                        {
                            Toast.makeText(this@ProfileActivity, it.message, Toast.LENGTH_SHORT).show()
                        },
                        showLoading = true
                    )
                }
            }
        }
    }

    private fun observe() {
        val colorBlack = getColor(R.color.black)
        val colorBlueDark400 = getColor(R.color.blue_dark_400)
        val colorOrange300 = getColor(R.color.orange_300)
        val colorWhite = getColor(R.color.white)

        profileViewModel.isLanguageVisible.observe(this) {
            if (it) {
                b.language.vgRoot.visibility = View.VISIBLE
                b.cvLanguage.setCardBackgroundColor(colorBlack)
                b.ivLanguage.setColorFilter(colorOrange300)
                b.tvLanguage.setTextColor(colorOrange300)
                b.ivLanguageArrow.setColorFilter(colorOrange300)
                b.ivLanguageArrow.rotation = 180f
            } else {
                b.language.vgRoot.visibility = View.GONE
                b.cvLanguage.setCardBackgroundColor(colorBlueDark400)
                b.ivLanguage.setColorFilter(colorWhite)
                b.tvLanguage.setTextColor(colorWhite)
                b.ivLanguageArrow.setColorFilter(colorWhite)
                b.ivLanguageArrow.rotation = 0f
            }
        }

        profileViewModel.isFavoritesVisible.observe(this) {
            if (it) {
                b.favorites.vgRoot.visibility = View.VISIBLE
                b.cvFavorites.setCardBackgroundColor(colorBlack)
                b.ivFavorites.setColorFilter(colorOrange300)
                b.tvFavorites.setTextColor(colorOrange300)
                b.ivFavoritesArrow.setColorFilter(colorOrange300)
                b.ivFavoritesArrow.rotation = 180f
                b.favorites.shimmerMovies.visibility = View.VISIBLE
                b.favorites.rvMovies.visibility = View.GONE
                b.favorites.shimmerSeries.visibility = View.VISIBLE
                b.favorites.rvSeries.visibility = View.GONE
                lifecycleScope.launch {
                    Movie.favorite(
                        { movies ->
                            b.favorites.shimmerMovies.visibility = View.GONE
                            b.favorites.rvMovies.visibility = View.VISIBLE
                            b.favorites.rvMovies.layoutManager = LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.HORIZONTAL, false)
                            b.favorites.rvMovies.adapter = AdapterRecyclerMovie().apply { submitList(movies) }
                        },
                        {
                            b.favorites.shimmerMovies.visibility = View.INVISIBLE
                        },
                        showLoading = false
                    )
                    Serie.favorite(
                        { series ->
                            b.favorites.shimmerSeries.visibility = View.GONE
                            b.favorites.rvSeries.visibility = View.VISIBLE
                            b.favorites.rvSeries.layoutManager = LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.HORIZONTAL, false)
                            b.favorites.rvSeries.adapter = AdapterRecyclerSerie().apply { submitList(series) }
                        },
                        {
                            b.favorites.shimmerSeries.visibility = View.INVISIBLE
                        },
                        showLoading = false
                    )
                }
            } else {
                b.favorites.vgRoot.visibility = View.GONE
                b.cvFavorites.setCardBackgroundColor(colorBlueDark400)
                b.ivFavorites.setColorFilter(colorWhite)
                b.tvFavorites.setTextColor(colorWhite)
                b.ivFavoritesArrow.setColorFilter(colorWhite)
                b.ivFavoritesArrow.rotation = 0f
            }
        }

        profileViewModel.isPurchasesVisible.observe(this) {
            if (it) {
                b.purchases.vgRoot.visibility = View.VISIBLE
                b.cvPurchases.setCardBackgroundColor(colorBlack)
                b.ivPurchases.setColorFilter(colorOrange300)
                b.tvPurchases.setTextColor(colorOrange300)
                b.ivPurchasesArrow.setColorFilter(colorOrange300)
                b.ivPurchasesArrow.rotation = 180f
                lifecycleScope.launch {
                    User.purchases(
                        {
                            b.purchases.rvPurchases.layoutManager = LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.VERTICAL, false)
                            b.purchases.rvPurchases.adapter = AdapterRecyclerPurchase(it)
                        },
                        showLoading = true
                    )
                }
            } else {
                b.purchases.vgRoot.visibility = View.GONE
                b.cvPurchases.setCardBackgroundColor(colorBlueDark400)
                b.ivPurchases.setColorFilter(colorWhite)
                b.tvPurchases.setTextColor(colorWhite)
                b.ivPurchasesArrow.setColorFilter(colorWhite)
                b.ivPurchasesArrow.rotation = 0f
            }
        }

        profileViewModel.isSubscriptionsVisible.observe(this) {
            if (it) {
                b.subscriptions.vgRoot.visibility = View.VISIBLE
                b.cvSubscriptions.setCardBackgroundColor(colorBlack)
                b.ivSubscriptions.setColorFilter(colorOrange300)
                b.tvSubscriptions.setTextColor(colorOrange300)
                b.ivSubscriptionsArrow.setColorFilter(colorOrange300)
                b.ivSubscriptionsArrow.rotation = 180f
                lifecycleScope.launch {
                    User.subscriptions(
                        {
                            b.subscriptions.rvSubscriptions.layoutManager = LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.VERTICAL, false)
                            b.subscriptions.rvSubscriptions.adapter = AdapterRecyclerSubscription(it)
                        },
                        showLoading = true
                    )
                }
            } else {
                b.subscriptions.vgRoot.visibility = View.GONE
                b.cvSubscriptions.setCardBackgroundColor(colorBlueDark400)
                b.ivSubscriptions.setColorFilter(colorWhite)
                b.tvSubscriptions.setTextColor(colorWhite)
                b.ivSubscriptionsArrow.setColorFilter(colorWhite)
                b.ivSubscriptionsArrow.rotation = 0f
            }
        }

        profileViewModel.isPasswordVisible.observe(this) {
            if (it) {
                b.password.vgRoot.visibility = View.VISIBLE
                b.cvPassword.setCardBackgroundColor(colorBlack)
                b.ivPassword.setColorFilter(colorOrange300)
                b.tvPassword.setTextColor(colorOrange300)
                b.ivPasswordArrow.setColorFilter(colorOrange300)
                b.ivPasswordArrow.rotation = 180f
            } else {
                b.password.vgRoot.visibility = View.GONE
                b.cvPassword.setCardBackgroundColor(colorBlueDark400)
                b.ivPassword.setColorFilter(colorWhite)
                b.tvPassword.setTextColor(colorWhite)
                b.ivPasswordArrow.setColorFilter(colorWhite)
                b.ivPasswordArrow.rotation = 0f
            }
        }

        ViewHelper.togglePasswordVisibility(b.password.ivToggleOldPassword, b.password.etOldPassword)
        ViewHelper.togglePasswordVisibility(b.password.ivToggleNewPassword, b.password.etNewPassword)
        ViewHelper.togglePasswordVisibility(b.password.ivToggleRepeatNewPassword, b.password.etRepeatNewPassword)
    }
}
