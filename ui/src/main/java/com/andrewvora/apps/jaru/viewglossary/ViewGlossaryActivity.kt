package com.andrewvora.apps.jaru.viewglossary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andrewvora.apps.jaru.R

class ViewGlossaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glossary_viewer)
        if (savedInstanceState == null) {
            val glossaryId = intent.extras?.getString(EXTRA_GLOSSARY_ID, "") ?: ""
            val fragment = ViewGlossaryFragment.create(glossaryId)

            supportFragmentManager.beginTransaction()
                .replace(R.id.glossary_viewer_fragment, fragment, "glossaryFragment")
                .commit()
        }
    }

    companion object {
        private const val EXTRA_GLOSSARY_ID = "glossaryId"

        fun start(context: Context, glossaryId: String): Intent {
            return Intent(context, ViewGlossaryActivity::class.java).apply {
                putExtra(EXTRA_GLOSSARY_ID, glossaryId)
            }
        }
    }
}