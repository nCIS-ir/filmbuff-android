package fragments

import activities.AuthActivity
import activities.MainActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goodiebag.pinview.Pinview
import com.goodiebag.pinview.Pinview.PinViewEventListener
import com.orhanobut.hawk.Hawk
import helpers.ContextHelper
import helpers.KeyHelper
import helpers.ViewHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.FragmentAuthOtpBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth
import java.util.Locale

class AuthOtpFragment(private val username: String, private val email: String) : Fragment() {
    private lateinit var b: FragmentAuthOtpBinding
    private lateinit var countDownTimer: CountDownTimer
    private var canClickResend = false
    private var otp = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentAuthOtpBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.tvError.visibility = View.INVISIBLE
        startTimer()

        b.tvResendCode.setOnClickListener {
            if (canClickResend) {
                lifecycleScope.launch {
                    Auth.resend(
                        username,
                        {
                            startTimer()
                        },
                        {
                            b.tvError.text = it.message
                            b.tvError.visibility = View.VISIBLE
                        })
                }
            }
        }

        b.otpView.setTextColor(ContextHelper.getColor(R.color.white))

        b.otpView.setPinViewEventListener(object : PinViewEventListener {
            override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
                b.btVerify.performClick()
            }
        })

        b.btVerify.setOnClickListener {
            ViewHelper.hideKeyboard(b.otpView)
            b.tvError.visibility = View.INVISIBLE
            otp = b.otpView.value.toInt()
            lifecycleScope.launch {
                Auth.verify(
                    username, otp,
                    {
                        Hawk.put(KeyHelper.TOKEN, it.token)
                        Hawk.put(KeyHelper.REFRESH, it.refresh)
                        lifecycleScope.launch {
                            Auth.info({ user ->
                                App.USER = user
                                App.ACTIVITY.runActivity(MainActivity::class.java, shouldFinish = true)
                            }, { exception ->
                                b.tvError.text = exception.message
                                b.tvError.visibility = View.VISIBLE
                            })
                        }
                    },
                    { exception ->
                        val statusCode = ContextHelper.getHttpStatus(exception)
                        val message = ContextHelper.getHttpMessage(exception)
                        b.tvError.text = when (statusCode) {
                            403 -> message
                            else -> exception.message
                        }
                        b.tvError.visibility = View.VISIBLE
                    })
            }
        }

        b.tvChange.setOnClickListener {
            (App.ACTIVITY as AuthActivity).showFragment(AuthRegisterFragment())
        }
    }

    private fun startTimer() {
        canClickResend = false
        countDownTimer = object : CountDownTimer(120_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished / 1_000
                val minutes = seconds / 60
                seconds %= 60
                App.HANDLER.post {
                    val resendCode = App.CONTEXT.getString(R.string.resend_code)
                    b.tvResendCode.text = String.format(Locale.US, "%s (%02d:%02d)", resendCode, minutes, seconds)
                }
            }

            override fun onFinish() {
                canClickResend = true
            }
        }
        countDownTimer.start()
    }
}