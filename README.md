# OpenSourceProgramming final project
---
## 시각 장애인을 위한 보행 안내서비스, 시야
object detection deeplearning model인 YOLO(You Only Look Once) version 8을 사용하여 시각장애인이 보행 중 위협 요소가 되는 각종 장애물을 피할 수 있도록 돕는 Android 기반 애플리케이션입니다.

<img src="https://github.com/jinsol24/OSS-project/assets/144991060/79ac8dcb-d835-46ab-97bc-db1b46fca0cc.png" width="180" height="400"/>
<img src="https://github.com/jinsol24/OSS-project/assets/144991060/09f8e6fb-8632-4d39-a056-0198621671e9.png" width="180" height="400"/>

---
## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [YOLOv8을 활용한 모델과 UI 설명](#YOLOv8을-활용한-모델과-UI-설명)
3. [활용 방법 설명:각 소스 코드 파일 설명](#활용-방법-설명각-소스-코드-파일-설명)
4. [한계점과 보완](#한계점과-보완)
5. [참고문헌](#참고문헌)
---
## 프로젝트 소개
+ 숙명여자대학교 IT공학전공(현 인공지능공학부) 오픈소스프로그래밍 수업의 기말 팀 프로젝트
+ 팀원: 김민지, 김진솔, 박세희
+ 개발 기간: 2024.05.19 ~ 2024.06.20
+ 주요 기능: 장애물 인식 후 TTS 및 진동으로 회피 방향 안내(단, 진동 모드는 화면 왼쪽을 터치하려 ON 시켜야 햐며, 진동 모드 ON시 TTS와 함께 진동 1번(왼쪽 회피)/2번(오서 코드를 확인할 수 있습니다.
   <details>
   <summary> AndroidManifest.xml </summary>
   인터넷 진동, 카메라 기능 권한을 추가하였고 화면 자동 회전을 비활성화했습니다.
   </details>

   <details>
   <summary> FirstActivity.kt </summary>
   activity_first.xml 화면과 함께 “안내를 시작합니다. 화면 왼쪽에 진동 모드 온오프 버튼이 있고, 오른쪽에 종료 버튼이 있습니다.”라는 문구의 TTS가 출력됩니다. 초기 화면이 8초간 지속된 후 메인 화면으로 넘어갑니다.
   </details>

   <details>
   <summary> MainActivity.kt </summary>
      
   -	onCreate: activity_main 화면에 YOLO 모델을 연결하고 진동 모드 ON/OFF, 앱 종료, TTS, 팝업의 기능이 작동하도록 구현하였습니다. 또 화면 절반을 기준으로 왼쪽을 터치하면 진동 모드 ON/OFF, 오른쪽을 터치하면 앱 종료 기능이 실행됩니다.

   -	setCamera: 카메라를 연결합니다.
     
   -	imageProcess: 이미지를 받아온 후 중앙 세로선을 기준으로 이미지가 왼쪽에 있으면 “전방에 [객체]이/가 있습니다. 오른쪽으로 피하세요.”, 오른쪽에 있으면 “전방에 [객체]이/가 있습니다. 왼쪽으로 피하세요.”라는 문구의 TTS가 출력됩니다. rectView로 객체 위치를 표시합니다.
     
   -	shouldShowPopup: 팝업과 TTS를 5초간 지속하는 함수입니다. 동시에 여러 객체가 인식될 경우 혼란을 방지하기 위해 한 객체에 대한 팝업과 TTS가 나오고 5초 뒤에 새로운 팝업과 TTS가 나오도록 구현했습니다.
     
   -	onDestroy: TTS를 종료하는 함수입니다.
     
   -	load: YOLO 모델을 불러오는 함수입니다.
     
   -	setPermissions, onRequestPermissionsResult: 카메라 권한 허용 여부에 따라 앱을 작동하거나 ”권한을 허용하지 않으면 사용할 수 없습니다.”라는 문구의 토스트 팝업을 출력합니다.
     
   -	closePopupWithAnimation: 팝업창 닫기 애니메이션을 관리하는 함수입니다.
     
   -	handlePopupDismiss: TTS와 팝업을 동기화하는 함수입니다.
     
   -	processNextDetection: 다음 TTS 처리를 위한 큐 메커니즘을 보여주는 함수입니다.
     
   -	stopAllTTS: 모든 TTS를 중지하는 함수입니다.
     
   -	onInit: 한국어 TTS만 출력하게 하는 함수입니다.
     
   -	translateObjectToKorean, loadObjectTranslations: 객체 탐지 결과를 한국어로 변환하는 함수입니다.
   </details>

   <details>
   <summary> DataProcess.kt, RectView.kt, Result.kt </summary>
   YOLO 모델 연결과 Bounding Box 표시에 관한 코드입니다. 다음 링크를 참고하였습니다. 
   <a href="https://velog.io/@aloe/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%B9%B4%EB%A9%94%EB%9D%BC-%EB%AF%B8%EB%A6%AC-%EB%B3%B4%EA%B8%B0" target="_blank">YOLO 모델 연결과 사용법</a>
   </details>

   <details>
   <summary> assets 폴더 </summary>
   yolov8n.onnx는 배포한 모델이며, yolov8n.txt는 학습된 객체 종류를 보여줍니다.
   </details>

   <details>
   <summary> ic_launcher_background.xml, ic_launcher_foreground.xml </summary>
   앱 아이콘 디자인에 관련된 레이아웃입니다.
   </details>

   <details>
   <summary> rounded_textbox.xml, activity_popup.xml </summary>
   팝업창 디자인에 관련된 레이아웃입니다.
   </details>

   <details>
   <summary> activity_first.xml, activity_main.xml </summary>
   각각 초기 화면, 메인 화면 디자인에 관련된 레이아웃입니다.
   </details>
---
## 한계점과 보완
+ 데이터를 저장할 수 있는 용량과 코랩의 GPU 사용량 제한으로 학습시킬 수 있는 데이터의 양에 한계가 있었고, 이로 인해 YOLO 모델의 정확도가 떨어집니다.
+ 횡단보도를 기다릴 때 지나가는 차량에 대한 경고 문구가 계속하여 발생합니다.
+ 거리는 판단 불가능하여 어떤 객체가 더 가까이 있는지 확인할 수 없습니다.(이를 위해 AI-Hub의 Depth Prediction 데이터셋을 활용 가능합니다.)
+ 객체를 동시에 여러 개 인식할 경우 혼란을 방지하기 위해 처음 나온 객체를 기준으로 5초 동안 TTS가 초기화되지 않도록 했으며, 이 때문에 차량의 갑작스러운 보도 침범 시 대처 능력이 떨어집니다.
+ 앱을 사용하는 동안 휴대폰의 다른 기능을 사용하기에 어려움이 있습니다.
+ 이후 인구 혼잡도를 반영한 경로 안내 기능(내비게이션 기능)을 추가하는 방향을 고려하고 있습니다.
