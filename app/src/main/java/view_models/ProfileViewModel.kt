package view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    val isLanguageVisible = MutableLiveData<Boolean>(false)
    val isFavoritesVisible = MutableLiveData<Boolean>(false)
    val isPurchasesVisible = MutableLiveData<Boolean>(false)
    val isSubscriptionsVisible = MutableLiveData<Boolean>(false)
    val isPasswordVisible = MutableLiveData<Boolean>(false)

    fun toggleLanguage() {
        isLanguageVisible.value = !isLanguageVisible.value!!
    }

    fun toggleFavorites() {
        isFavoritesVisible.value = !isFavoritesVisible.value!!
    }

    fun togglePurchases() {
        isPurchasesVisible.value = !isPurchasesVisible.value!!
    }

    fun toggleSubscriptions() {
        isSubscriptionsVisible.value = !isSubscriptionsVisible.value!!
    }

    fun togglePassword() {
        isPasswordVisible.value = !isPasswordVisible.value!!
    }
}