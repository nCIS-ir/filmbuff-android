package dialogs

import android.content.Context
import android.os.Bundle
import helpers.ImageHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.DialogEnhanced
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.DialogCastDetailsBinding
import retrofit.models.Cast

class CastDetailsDialog(context: Context, private val cast: Cast) : DialogEnhanced(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = DialogCastDetailsBinding.inflate(layoutInflater)
        setContentView(b.root)

        ImageHelper(cast.artist.photo, R.mipmap.placeholder).loadInto(b.ivArtist)
        ImageHelper(App.DB.countryDao().one(cast.artist.countryId).flag, R.mipmap.placeholder).loadInto(b.ivCountry)
        b.tvName.text = cast.artist.fullName
        b.tvRoleName.text = cast.name
        b.tvBiography.text = cast.artist.biography
        b.tvRoleDescription.text = cast.description
    }
}