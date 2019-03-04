package com.andrewvora.apps.jaru.tts

import android.app.Application
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.andrewvora.apps.jaru.R
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechHelper
@Inject
constructor(private val app: Application) {

    private var initialized = false
    private val textToSpeechService: TextToSpeech by lazy {
        TextToSpeech(app, TextToSpeech.OnInitListener {
            initialized = it == TextToSpeech.SUCCESS
            textToSpeechService.language = Locale.forLanguageTag("th-TH")
        })
    }

    fun init() {
        textToSpeechService
    }

    fun hasSpeechRecognizer(): Boolean {
        return SpeechRecognizer.isRecognitionAvailable(app)
    }

    fun speechToText(fragment: Fragment) {
        fragment.startActivityForResult(getSpeechRecognizerIntent(), REQUEST_CODE)
    }

    fun speechToText(activity: AppCompatActivity) {
        activity.startActivityForResult(getSpeechRecognizerIntent(), REQUEST_CODE)
    }

    private fun getSpeechRecognizerIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            this.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH")

            val instructions = app.getString(R.string.speech_to_text_instruction)
            this.putExtra(RecognizerIntent.EXTRA_PROMPT, instructions)
        }
    }

    fun getTextFromBundle(intent: Intent?): String {
        return intent?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.first() ?: ""
    }

    fun textToSpeech(text: String) {
        textToSpeechService.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            UUID.randomUUID().toString())
    }

    companion object {
        const val REQUEST_CODE = 100
    }
}