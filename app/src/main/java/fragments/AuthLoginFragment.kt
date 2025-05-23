package fragments

import activities.AuthActivity
import activities.MainActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import helpers.KeyHelper
import helpers.ViewHelper
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
            ViewHelper.hideKeyboard(b.etUsername)
            ViewHelper.hideKeyboard(b.etPassword)
            val username = b.etUsername.editableText.toString()
            val password = b.etPassword.editableText.toString()
            if (username.isEmpty() || password.isEmpty()) {
                b.tvError.text = getString(R.string.required)
                b.tvError.visibility = View.VISIBLE
            } else {
                lifecycleScope.launch {
                    Auth.login(username, password, {
                        Hawk.put(KeyHelper.TOKEN, it.token)
                        Hawk.put(KeyHelper.REFRESH, it.refresh)
                        lifecycleScope.launch {
                            Auth.info({ user ->
                                App.USER = user
                                App.ACTIVITY.runActivity(MainActivity::class.java, shouldFinish = true)
                            }, {
                                b.tvError.text = it.message
                                b.tvError.visibility = View.VISIBLE
                            })
                        }
                    }, {
                        b.tvError.text = it.message
                        b.tvError.visibility = View.VISIBLE
                    })
                }
            }
        }

        ViewHelper.togglePasswordVisibility(b.ivTogglePassword, b.etPassword)
    }
}