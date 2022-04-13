package com.example.implicitintents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ShareCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mWebsiteEditText: EditText
    private lateinit var mLocationEditText: EditText
    private lateinit var mShareTextEditText: EditText
    private lateinit var mTakeAPicBtn: Button

    private val mPictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWebsiteEditText = findViewById(R.id.website_edittext)
        mLocationEditText = findViewById(R.id.location_edittext)
        mShareTextEditText = findViewById(R.id.share_edittext)

        mTakeAPicBtn = findViewById(R.id.take_pic_button)
        mPictureIntent.resolveActivity(packageManager)?.let {
            mTakeAPicBtn.setOnClickListener { startActivity(mPictureIntent) }
        }
    }

    fun openWebsite(view: View) {
        val url = mWebsiteEditText.text?.toString()
        url?.let {
            val webpage = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else Log.d("ImplicitIntents", "Can't handle this!")
        }
    }

    fun openLocation(view: View) {
        val loc = mLocationEditText.text?.toString()
        loc?.let {
            val addressUri = Uri.parse("geo:0,0?q=$loc")
            val intent = Intent(Intent.ACTION_VIEW, addressUri)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else Log.d("ImplicitIntents", "Can't handle this!")
        }
    }

    fun shareTex(view: View) {
        val txt = mShareTextEditText.text?.toString()
        val mimeType = "text/plain"
        val chooser = ShareCompat.IntentBuilder(this)
            .setType(mimeType)
            .setChooserTitle(getString(R.string.chooser_title))
            .setText(txt)
        chooser.startChooser()
    }
}