package dialogs

import android.content.Context
import android.os.Bundle
import ir.ncis.filmbuff.DialogEnhanced
import ir.ncis.filmbuff.databinding.DialogConfirmBinding

class ConfirmDialog(context: Context, private val title: String, private val message: String, private val onConfirm: () -> Unit) : DialogEnhanced(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = DialogConfirmBinding.inflate(this.layoutInflater)
        setContentView(b.root)
        setCancelable(false)

        b.tvTitle.text = title
        b.tvMessage.text = message

        b.cvConfirm.setOnClickListener {
            onConfirm()
            dismiss()
        }

        b.cvReject.setOnClickListener {
            dismiss()
        }
    }
}