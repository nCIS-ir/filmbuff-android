package activities

import adapters.AdapterRecyclerMovie
import adapters.AdapterRecyclerSerie
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.hawk.Hawk
import dialogs.ConfirmDialog
import helpers.LocaleHelper
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth
import retrofit.calls.Movie
import retrofit.calls.Serie
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

        b.favorites.vgRoot.setOnClickListener {
            lifecycleScope.launch {
                Movie.favorite(
                    { movies ->
                        b.favorites.rvMovies.layoutManager = LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = AdapterRecyclerMovie()
                        b.favorites.rvMovies.adapter = adapter
                        adapter.submitList(movies)
                    }, showLoading = false
                )
                Serie.favorite(
                    { series ->
                        b.favorites.rvSeries.layoutManager = LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = AdapterRecyclerSerie()
                        b.favorites.rvSeries.adapter = adapter
                        adapter.submitList(series)
                    }, showLoading = false
                )
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
    }
}
