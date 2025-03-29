package fragments

import activities.AuthActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.FragmentAuthRegisterBinding

class AuthRegisterFragment() : Fragment() {
    private lateinit var b: FragmentAuthRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentAuthRegisterBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.tvLogin.setOnClickListener {
            (App.ACTIVITY as AuthActivity).showFragment(AuthLoginFragment())
        }
    }
}