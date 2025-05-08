package dialogs

import android.content.Context
import android.os.Bundle
import ir.ncis.filmbuff.DialogEnhanced
import ir.ncis.filmbuff.databinding.DialogSortingBinding

class SortingDialog(context: Context) : DialogEnhanced(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = DialogSortingBinding.inflate(layoutInflater)
        setContentView(b.root)

    }
}