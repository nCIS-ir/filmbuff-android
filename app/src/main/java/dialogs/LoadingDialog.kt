package dialogs

import android.content.Context
import android.os.Bundle
import ir.ncis.filmbuff.DialogEnhanced
import ir.ncis.filmbuff.databinding.DialogLoadingBinding

class LoadingDialog(context: Context, private val title: String? = null) : DialogEnhanced(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = DialogLoadingBinding.inflate(this.layoutInflater)
        setContentView(b.root)
        setCancelable(false)

        title?.let { b.tvTitle.text = it }
    }
}