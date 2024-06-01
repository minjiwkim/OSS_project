package com.example.myapplication1

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var mainLayout: ConstraintLayout
    private lateinit var editText: EditText
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var popupWindow: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<View>(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // TTS 초기화
        textToSpeech = TextToSpeech(this, this)

        // 뷰 초기화
        editText = findViewById(R.id.editText)

        // EditText에 TextWatcher 추가
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 아무 것도 안함
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경될 때마다 TTS로 읽음
                val text = s.toString()
                if (text.isNotEmpty()) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // 아무 것도 하지 않음
            }
        })

        showPopupWithAnimation()

        /* TTS 설정 버튼 클릭 리스너 추가
        findViewById<Button>(R.id.ttsSettingsButton).setOnClickListener {
            // TTS 설정 다이얼로그 표시
            val intent = Intent()
            intent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
            startActivity(intent)
        }
        */
    }

    private fun showPopupWithAnimation() {
        // 팝업창 레이아웃 인플레이트
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_popup, null)

        // 팝업창 생성
        popupWindow = PopupWindow(popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        // 애니메이션 로드
        val slideInUp = AnimationUtils.loadAnimation(this, R.anim.slide_in_up)
        val slideOutDown = AnimationUtils.loadAnimation(this, R.anim.slide_out_down)

        // 팝업창 보여주기
        popupWindow.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, 0)
        popupView.startAnimation(slideInUp)

        // 5초 후 팝업창 닫기 애니메이션 시작
        Handler().postDelayed({
            popupView.startAnimation(slideOutDown)
            // 애니메이션 끝난 후 팝업창 닫기
            slideOutDown.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    popupWindow.dismiss()
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }, 7000) // 총 7초 대기 (2초 애니메이션 + 5초 대기)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.KOREAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "언어를 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "초기화에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        if (this::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}