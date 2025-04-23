package fragments

import activities.MainActivity
import activities.SplashActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import dialogs.ConfirmDialog
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.FragmentMainProfileBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth

class MainProfileFragment : Fragment() {
    private lateinit var b: FragmentMainProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentMainProfileBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.ivBack.setOnClickListener { (requireActivity() as MainActivity).showFragment(MainHomeFragment()) }

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