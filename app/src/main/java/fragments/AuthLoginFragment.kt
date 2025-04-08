package fragments

import activities.AuthActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import helpers.KeyString
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.FragmentAuthLoginBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth

class AuthLoginFragment() : Fragment() {
    private lateinit var b: FragmentAuthLoginBinding
    private var isPasswordVisible = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentAuthLoginBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.tvError.visibility = View.INVISIBLE

        b.tvRegister.setOnClickListener {
            (App.ACTIVITY as AuthActivity).showFragment(AuthRegisterFragment())
        }

        b.btLogin.setOnClickListener {
            val username = b.etUsername.editableText.toString()
            val password = b.etPassword.editableText.toString()
            if (username.isEmpty() || password.isEmpty()) {
                b.tvError.text = getString(R.string.required)
                b.tvError.visibility = View.VISIBLE
            } else {
                lifecycleScope.launch {
                    Auth.login(username, password)
                        .onSuccess {
                            Hawk.put(KeyString.TOKEN, it.token)
                            Hawk.put(KeyString.REFRESH, it.refresh)
                            Auth.info()
                                .onSuccess { user -> App.USER = user }
                                .onFailure {
                                    b.tvError.text = it.message
                                    b.tvError.visibility = View.VISIBLE
                                }
                        }
                        .onFailure {
                            b.tvError.text = it.message
                            b.tvError.visibility = View.VISIBLE
                        }
                }
            }
        }

        b.ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            b.ivTogglePassword.setImageResource(if (isPasswordVisible) R.drawable.ic_eye_close else R.drawable.ic_eye_open)
            b.etPassword.inputType = InputType.TYPE_CLASS_TEXT or if (isPasswordVisible) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD else InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }
}