package com.example.myapplication1

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import java.util.*
import android.os.*
import android.view.*
import android.view.animation.*
import android.widget.*
import androidx.core.view.*
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale
import retrofit2.*
import android.content.pm.PackageManager
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var previewView: PreviewView

    private lateinit var vibrator: Vibrator
    private var isVibrateModeOn: Boolean = false

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var popupWindow: PopupWindow
    private val handler = Handler(Looper.getMainLooper())
    private val exampleTexts = listOf(
        "전방에 자전거가 있습니다. 오른쪽으로 피하세요.",
        "전방에 자동차가 있습니다. 왼쪽으로 피하세요."
    )

    private var isExiting: Boolean = false

    companion object{
        const val PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        isVibrateModeOn = getSharedPreferences("settings", MODE_PRIVATE).getBoolean("vibrate_mode", false)

        val main = findViewById<View>(R.id.main)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        previewView = findViewById(R.id.previewView)

        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 화면 터치 이벤트 처리
        val mainView = findViewById<View>(R.id.main)
        mainView.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // 화면의 절반을 기준으로 왼쪽과 오른쪽을 구분
                    if (event.x < mainView.width / 2) {
                        // 왼쪽 절반을 터치한 경우 진동 모드 토글
                        toggleVibrateMode()
                    } else {
                        // 오른쪽 절반을 터치한 경우 앱 종료
                        exitApp()
                    }
                }
            }
            true
        }

        //권한 허용
        setPermissions()

        //카메라 켜기
        setCamera()

        textToSpeech = TextToSpeech(this, this)

        button1.setOnClickListener {
            // 버튼1의 동작이 앱 종료 상태일 때는 동작하지 않도록 함
            if (!isExiting) {
                // 진동 모드 토글 기능 실행
                toggleVibrateMode()
                // 클릭한 버튼의 배경색 변경
                button1.setBackgroundColor(Color.GRAY)
                // 200 밀리초 후에 버튼의 배경색을 원래대로 변경
                handler.postDelayed({
                    button1.setBackgroundColor(Color.parseColor("#0070C0"))
                }, 200)
            }
        }

        button2.setOnClickListener {
            // 앱 종료 기능 실행
            exitApp()
            // 클릭한 버튼의 배경색 변경
            button2.setBackgroundColor(Color.GRAY)
            // 200 밀리초 후에 버튼의 배경색을 원래대로 변경
            handler.postDelayed({
                button2.setBackgroundColor(Color.parseColor("#0070C0"))
            }, 200)
        }

        // 팝업창 읽어주는 TTS 설정
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) {
                if (utteranceId == "exitTTS" && isExiting) {
                    runOnUiThread {
                        finishAffinity()
                    }
                } else if (utteranceId == "popupTTS") {
                    runOnUiThread {
                        closePopupWithAnimation()
                    }
                }
            }

            override fun onError(utteranceId: String?) {}
        })
    }

    // 앱이 종료되고 있을 때 새로운 설명 제공하지 않음
    private fun announceDescription(description: String) {
        if (!isExiting) {
            showPopup(description)
            // "왼쪽" 또는 "오른쪽"이 포함되어 있으면 진동 추가
            if (description.contains("왼쪽")) {
                if (isVibrateModeOn) {
                    vibrateMultipleTimes(1)
                }
            } else if (description.contains("오른쪽")) {
                if (isVibrateModeOn) {
                    vibrateMultipleTimes(2)
                }
            }
        }
    }

    // 진동 간격 설정
    private fun vibrateMultipleTimes(times: Int) {
        val pattern = LongArray(times * 2) { if (it % 2 == 0) 200L else 200L } // 200ms 진동 후 200ms 대기, times번 반복
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            vibrator.vibrate(pattern, -1)
        }
    }

    // 진동 모드 설정
    private fun toggleVibrateMode() {
        // 이전 진동 모드 상태 저장
        val prevVibrateMode = isVibrateModeOn

        // 진동 모드를 토글
        isVibrateModeOn = !isVibrateModeOn

        // 진동 모드가 켜져 있는 경우에 진동 실행
        if (isVibrateModeOn && !prevVibrateMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(500)
            }
        } else if (!isVibrateModeOn && prevVibrateMode) {
            // 진동 모드가 꺼져 있는 경우에 진동 취소
            vibrator.cancel()
        }

        // TTS로 진동 모드 변경 안내
        val message = if (isVibrateModeOn) {
            "진동 모드를 켰습니다."
        } else {
            "진동 모드를 껐습니다."
        }
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "vibrateTTS")
        }
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, params, "vibrateTTS")

        // 진동 모드 상태 저장
        getSharedPreferences("settings", MODE_PRIVATE).edit().putBoolean("vibrate_mode", isVibrateModeOn).apply()
    }


    // 앱 종료 설정
    private fun exitApp() {
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "exitTTS")
        }
        textToSpeech.speak("안내를 종료합니다.", TextToSpeech.QUEUE_FLUSH, params, "exitTTS")

        if (!isExiting) {
            isExiting = true
        }
    }

    // 팝업창 설정
    private fun showPopup(text: String) {
        // 기존 팝업이 열려 있다면 닫기
        if (::popupWindow.isInitialized && popupWindow.isShowing) {
            popupWindow.dismiss()
        }

        // 다른 TTS 중지
        stopAllTTS()

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_popup, null)
        popupWindow = PopupWindow(popupView, WRAP_CONTENT, WRAP_CONTENT)

        val slideUp = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f,
            Animation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = 1000
        }

        /* 화면을 클릭하면 TTS를 다시 읽어 줌
        popupView.animation = slideUp
        popupView.setOnClickListener {
            // TTS 중지
            stopAllTTS()
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "popupTTS")
        }
         */

        val mainView = findViewById<View>(R.id.main)
        mainView.post {
            popupWindow.showAtLocation(mainView, Gravity.CENTER, 0, 0)
            popupView.startAnimation(slideUp)

            val params = Bundle().apply {
                putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "popupTTS")
            }
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params, "popupTTS")
        }
    }

    private fun setCamera() {
        //카메라 제공 객체
        val processCameraProvider = ProcessCameraProvider.getInstance(this).get()

        //전체 화면
        previewView.scaleType = PreviewView.ScaleType.FILL_CENTER

        // 전면 카메라
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        // 16:9 화면으로 받아옴
        val preview = Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9).build()

        // preview 에서 받아와서 previewView 에 보여준다.
        preview.setSurfaceProvider(previewView.surfaceProvider)

        // 카메라의 수명 주기를 메인 액티비티에 귀속
        processCameraProvider.bindToLifecycle(this, cameraSelector, preview)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION) {
            grantResults.forEach {
                if (it != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한을 허용하지 않으면 사용할 수 없습니다", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setPermissions() {
        val permissions = ArrayList<String>()
        permissions.add(android.Manifest.permission.CAMERA)

        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions.toTypedArray(), PERMISSION)
            }
        }
    }

    // 팝업창 닫기 애니메이션
    private fun closePopupWithAnimation() {
        val slideDown = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f
        ).apply {
            duration = 500
        }

        val popupView = popupWindow.contentView
        popupView.startAnimation(slideDown)
        slideDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                runOnUiThread {
                    popupWindow.dismiss()
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    // 모든 TTS 중지
    private fun stopAllTTS() {
        // 현재 TTS 중지
        if (::textToSpeech.isInitialized && textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
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
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}