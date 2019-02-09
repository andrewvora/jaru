package com.andrewvora.apps.jaru.questionsets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import com.andrewvora.apps.jaru.R
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class QuestionSetsActivity : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech
    private var ttsInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        textToSpeech = TextToSpeech(application, TextToSpeech.OnInitListener {
            ttsInitialized = it == TextToSpeech.SUCCESS
            textToSpeech.language = Locale.forLanguageTag("th-TH")
        })

//        record_button.isEnabled = SpeechRecognizer.isRecognitionAvailable(this)
//        record_button.setOnClickListener {
//            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
//                this.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH")
//                this.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak")
//            }
//
//            startActivityForResult(intent, 0)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let {
//            textView.text = it.first()

            if (ttsInitialized) {
                textToSpeech.speak(it.first(), TextToSpeech.QUEUE_FLUSH, null, UUID.randomUUID().toString())
            }
        }
    }
}
