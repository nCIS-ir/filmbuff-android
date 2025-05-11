package fragments

import activities.AuthActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import helpers.ContextHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.FragmentAuthRegisterBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth

class AuthRegisterFragment() : Fragment() {
    private lateinit var b: FragmentAuthRegisterBinding
    private var isPasswordVisible = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentAuthRegisterBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.tvError.visibility = View.INVISIBLE

        b.tvLogin.setOnClickListener {
            (App.ACTIVITY as AuthActivity).showFragment(AuthLoginFragment())
        }

        b.btRegister.setOnClickListener {
            b.tvError.visibility = View.INVISIBLE
            val username = b.etUsername.editableText.toString()
            val password = b.etPassword.editableText.toString()
            val email = b.etEmail.editableText.toString()
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                b.tvError.text = getString(R.string.required)
                b.tvError.visibility = View.VISIBLE
            } else {
                lifecycleScope.launch {
                    Auth.register(
                        username, password, email,
                        {
                            (App.ACTIVITY as AuthActivity).showFragment(AuthOtpFragment(username, email))
                        },
                        {
                            b.tvError.text = it.message
                            b.tvError.visibility = View.VISIBLE
                        })
                }
            }
        }

        ContextHelper.togglePassword(b.ivTogglePassword, b.etPassword)
    }
}