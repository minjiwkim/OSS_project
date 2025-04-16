# 📱 Seeya(Vision Assist) – Navigation Guide App for the Visually Impaired  
### シヤ(視野) – 視覚障害者のための歩行ナビアプリ

---

## 🧩 Project Overview | プロジェクト概要

**EN**  
Seeya(Vision Assist) is an Android-based navigation assistant app designed to help visually impaired individuals avoid obstacles in real time. It integrates a YOLOv8 object detection model trained on pedestrian walkway video data from AI Hub. The app provides multimodal feedback through Text-to-Speech (TTS), vibration, and sound effects, with a UI/UX designed for accessibility and ease of use.

**JP**  
シヤ（視野）は、視覚障がい者が安全に歩行できるよう支援するAndroidアプリです。AI Hubの歩道映像データで訓練されたYOLOv8物体検出モデルを使用し、リアルタイムで障害物を検出します。音声読み上げ（TTS）、振動、効果音を通じてユーザーに障害物回避を案内し、アクセシビリティに配慮したUI/UXを実現しました。

---

## 🛠️ Technologies | 使用技術

- YOLOv8 (Ultralytics)
- Android Studio (Kotlin)
- Google Text-to-Speech API
- ONNX / TensorFlow Lite
- Google Colab (Model Training)

---

## 💡 Key Features | 主な機能

- ✅ Real-time object detection for pedestrian walkways  
  歩道向けのリアルタイム物体検出  
- ✅ Multimodal user guidance (TTS, vibration, sound)  
  音声・振動・効果音による多モーダル案内  
- ✅ Pop-up alerts and intuitive navigation cues  
  ポップアップ通知と直感的なナビゲーション  
- ✅ Minimal and accessible UI design  
  シンプルかつアクセシブルなユーザインタフェース  

---

## 📂 Role & Contributions | 担当・貢献内容

**EN**  
- Trained a YOLOv8 model using AI Hub pedestrian walkway dataset  
- Converted model to ONNX and TensorFlow Lite for Android integration  
- Developed Android app including UI, TTS, vibration and alert system  
- Designed and implemented accessibility-focused user experience  

**JP**  
- AI Hubの歩道データを用いてYOLOv8モデルを訓練  
- ONNXおよびTensorFlow Lite形式でAndroid用に変換・統合  
- UI、TTS、振動、アラートシステムを含むAndroidアプリを開発  
- アクセシビリティ重視のユーザー体験設計を実施  

---

## 🎯 Goal | 目標

Create a real-world usable, open-source app that empowers visually impaired individuals to walk safely and independently using advanced AI technology.

先進的なAI技術を活用し、視覚障がい者が安全かつ自立して歩行できるよう支援する、実用的でオープンソースなアプリの開発を目指しました。

---

## 📎 Links

- 🔗 [YOLOv8 - Ultralytics](https://github.com/ultralytics/ultralytics)
- 🔗 [AI Hub Dataset](https://aihub.or.kr)

---

# 📚 Korean Version

## 시각 장애인을 위한 보행 안내서비스, 시야 

object detection deeplearning model인 YOLO(You Only Look Once) version 8을 사용하여 시각장애인이 보행 중 위협 요소가 되는 각종 장애물을 피할 수 있도록 돕는 Android 기반 애플리케이션입니다.

<img src="https://github.com/jinsol24/OSS-project/assets/144991060/79ac8dcb-d835-46ab-97bc-db1b46fca0cc.png" width="180" height="400"/>
<img src="https://github.com/jinsol24/OSS-project/assets/144991060/09f8e6fb-8632-4d39-a056-0198621671e9.png" width="180" height="400"/>

---
## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [YOLOv8을 활용한 모델과 UI 설명](#YOLOv8을-활용한-모델과-UI-설명)
3. [활용 방법 설명:각 소스 코드 파일 설명](#활용-방법-설명각-소스-코드-파일-설명)
4. [한계점과 보완점](#한계점과-보완점)
---
## 프로젝트 소개
+ 공개SW 개발자 대회 출품작
+ 팀원: 김민지, 김진솔, 박세희
+ 개발 기간: 2024.07.04 ~ 2024.08.28
+ 주요 기능: 장애물 인식 후 TTS와 효과음으로 회피 방향 안내(단, 진동 모드는 화면 왼쪽을 터치하여 ON/OFF할 수 있으며, 진동 모드 ON시 효과음 대신 진동 1번(왼쪽 회피)/2번(오른쪽 회피) 울림)
+ 사용 툴: Android Studio Jellyfish, Colab, GitHub
+ 설치 및 실행 방법: Android Sudio를 설치하고 Code-clone한 후 Front-end/AndroidProject를 실행합니다. 컴퓨터와 연결할 휴대폰에 맞는 드라이버를 컴퓨터에 다운로드한 후 설치합니다(삼성 휴대폰인 경우 [통합 USB 드라이버 설치]( https://www.samsungsvc.co.kr/download)). 안드로이드 기반 스마트폰의 설정-휴대전화 정보-소프트웨어 정보에서 빌드 번호 5번을 누르면 개발자 옵션이 생기는데, 이 옵션에서 USB 디버깅을 켜고 휴대폰과 컴퓨터를 USB 선으로 연결합니다. Android Studio에서 run을 누르면 휴대폰에서 앱이 실행됩니다.
+ 라이선스: MIT license
---
## YOLOv8을 활용한 모델과 UI 설명
1. YOLOv8 모델
   - YOLOv8은 2023년 1월 발표된 버전으로, 현재 YOLOv10까지 발표되었으나 자료가 풍부한 YOLOv8을 채택했습니다. (2024.06.21 기준)
   - YOLO는 실시간 객체 검출 알고리즘으로, 객체 검출을 단 한번의 순전파만으로 수행하는 효율적인 방법입니다.
   - AI-Hub의 인도보행 영상 Bounding Box 데이터셋을 활용해 모델을 훈련시켰습니다.([데이터셋 링크](https://www.aihub.or.kr/aihubdata/data/view.do?currMenu=&topMenu=&aihubDataSe=data&dataSetSn=189))
   - 데이터를 저장햘 용량 문제와 GPU 문제로 약 20000개의 데이터밖에 활용 못했지만, 성능 향상을 위해 훨씬 더 많은 데이터를 사용할 것을 권장드립니다.
   - YOLOv8 CLI를 통해 모델을 train, validation, test 하였습니다.
2. UI
   - 화면 상단의 좌측에는 진동 모드 ON/OFF 버튼, 우측에는 종료 버튼을 배치했습니다.
   - 사용자가 시각장애인임을 고려하여 꼭 버튼이 아니더라도 화면 왼쪽/오른쪽만 터치해도 버튼 기능이 작동하도록 하였습니다.
   - 애플리케이션이 잘 실행되는지 확인하기 위해 화면 중앙에 카메라 화면을 띄웠습니다.
   - 음성과 함께 팝업창으로 위험요소 회피 문구 안내하도록 했습니다. (팝업창은 애플리케이션이 잘 실행되는지 확인하기 위해 띄움)
---
## 활용 방법 설명: 각 소스 코드 파일 설명

1. YOLOv8 폴더 설명


   Colab의 GPU와 Google Drive을 사용해 YOLOv8 모델을 학습시켰습니다. 따라서 Colab에서 GPU를 연결한 뒤, Google Drive를 mount해야 합니다.

   <details>
   <summary> xmlToyolo.ipynb </summary>


     XML 형식의 라벨링 format을 YOLO format으로 변경해 저장하는 소스코드입니다. AI-hub의 인도보행 Bounding Box 데이터셋은 Bbox_****(ex. Bbox_0001) 폴더 안에 평균적으로 100개의 이미지와 1개의 xml 형식의 라벨을 포함하고 있습니다. 이 xml 형식의 라벨 format을 YOLO format으로 변경해줘야 합니다.

   이미지를 저장하는 images 폴더와 라벨을 저장하는 labels 폴더를 따로 만들어 저장하는 것을 권장드립니다.(추후, 제대로 저장되었는지 확인하기 위해)

   labes 폴더 밑에 Bbox_****(ex. Bbox_0001) 형식의 폴더를 만들어 해당하는 라벨을 하나씩 따로 저장하는 것을 권장드립니다. 이 폴더에 YOLO format으로 변경된 라벨을 각각 저장해줍니다.

   class_mapping에는 데이터셋의 class 이름과 인덱스가 들어갑니다. 다른 데이터셋을 사용한다면 이를 수정해야 합니다.

   사용하는 사람의 파일 경로에 맞게 경로를 수정해줘야 합니다. 
   </details>

   <details>
   <summary> checkData.ipynb </summary>

      
   AI-hub에서 인도보행 Bounding Box 데이터셋을 다운로드 받으면 여러 개의 폴더(`Bbox_****`)로 나누어져 있습니다. Bbox_****(ex. Bbox_0001) 이름의 폴더 안에 파일이 제대로 저장되었는지 확인하는 소스 코드 파일입니다.

     해당 소스코드의 텍스트(주석)을 참고하면 더 도움이 될 수 있습니다.

     labels 폴더로 label이 있는 XML 파일을 옮기는 소스 코드가 포함되어 있습니다. 이는 xmlTOyolo.ipynb 파일을 실행하기 전에 수행하는 것을 권장드립니다.

     위의 코드가 수행되었다면 images/Bbox_****과 labels/Bbox_****에는 각각 이미지와 이 이미지에 해당하는 라벨이 저장되어 있습니다.
     제대로 저장되어 있는지 확인하기 위해 소스코드의 '폴더에 있는 파일 개수 세기'를 실행합니다. `images/Bbox_****`과 `labels/Bbox_****` 안에 있는 파일 개수 차이가 1이라면 제대로 저장된 것입니다. (labels/Bbox_****에는 xml 파일이 있기 때문에 파일 개수가 하나 더 많습니다.)

     제대로 저장이 안 되어 있다면 폴더를 확인하여 중복되거나 없는 이미지 파일, 라벨 파일이 있는지 확인해 직접 수정합니다.

     제대로 저장되었다면 'xml 파일 지우기' 부분을 실행하여 xml 파일을 삭제합니다.

     삭제 후, '정리된 label과 image를 my_data/train으로 옮기기'를 실행합니다. 이후, my_data/test와 my_data/val에도 같은 작업을 수행해 모델 훈련을 준비합니다.(단, 폴더 구조는 아래와 같아야 합니다.)

     <img src="https://github.com/jinsol24/OSS-project/assets/144991060/e90f54ad-0622-4398-817a-a5128db448f6.png" width="190" height="300"/>
   </details>

   <details>
   <summary>data.yaml</summary>


     YOLOv8 기준 yaml 파일입니다.

     자신이 활용하는 데이터셋의 클래스와 폴더 경로를 참고하여 작성해주세요. PATH에는 데이터 경로가 들어가야 하며, train, test, val에는 이미지 폴더의 경로가 들어가야 합니다.
   </details>

   <details>
   <summary>yolov8.ipynb</summary>


     YOLOv8 CLI를 활용해 모델을 훈련 시킵니다.

     파일의 위치를 제대로 확인해야 합니다. data.yaml파일의 경로가 해당 위치와 같은 곳에 있는지 확인해주세요. 같은 곳에 있지 않다면 경로를 수정해주세요. (이에 해당하는 코드도 파일에 있습니다)

     제대로 되었다면 train, test, val을 순서대로 실행해줍니다.

     Android Studio에서 학습된 모델을 활용하기 위해 onnx 형태로 export 해 줍니다.
   </details>

2. UI 코드 설명

   Front-end/AndroidProject/app/src/main에서 코드를 확인할 수 있습니다.
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

      
   -	onCreate

     activity_main 화면에 YOLO 모델을 연결하고 진동 모드 ON/OFF, 앱 종료, TTS, 팝업의 기능이 작동하도록 구현하였습니다. 또 화면 절반을 기준으로 왼쪽을 터치하면 진동 모드 ON/OFF, 오른쪽을 터치하면 앱 종료 기능이 실행됩니다.

   -	setCamera

     카메라를 연결합니다.
     
   -	imageProcess
 
     이미지를 받아온 후 중앙 세로선을 기준으로 이미지가 왼쪽에 있으면 “전방에 [객체]이/가 있습니다. 오른쪽으로 피하세요.”, 오른쪽에 있으면 “전방에 [객체]이/가 있습니다. 왼쪽으로 피하세요.”라는 문구의 TTS가 출력됩니다. rectView로 객체 위치를 표시합니다.
     
   -	shouldShowPopup

     팝업과 TTS를 5초간 지속하는 함수입니다. 동시에 여러 객체가 인식될 경우 혼란을 방지하기 위해 한 객체에 대한 팝업과 TTS가 나오고 5초 뒤에 새로운 팝업과 TTS가 나오도록 구현했습니다.
     
   -	onDestroy

     TTS를 종료하는 함수입니다.
     
   -	load

     YOLO 모델을 불러오는 함수입니다.
     
   -	setPermissions, onRequestPermissionsResult

     카메라 권한 허용 여부에 따라 앱을 작동하거나 ”권한을 허용하지 않으면 사용할 수 없습니다.”라는 문구의 토스트 팝업을 출력합니다.
     
   -	closePopupWithAnimation

     팝업창 닫기 애니메이션을 관리하는 함수입니다.
     
   -	handlePopupDismiss

     TTS와 팝업을 동기화하는 함수입니다.
     
   -	processNextDetection
 
     다음 TTS 처리를 위한 큐 메커니즘을 보여주는 함수입니다.
     
   -	stopAllTTS

     모든 TTS를 중지하는 함수입니다.
     
   -	onInit

     한국어 TTS만 출력하게 하는 함수입니다.
     
   -	translateObjectToKorean, loadObjectTranslations

     객체 탐지 결과를 한국어로 변환하는 함수입니다.
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
## 한계점과 보완점
+ 데이터를 저장할 수 있는 용량과 코랩의 GPU 사용량 제한으로 학습시킬 수 있는 데이터의 양에 한계가 있었고, 이로 인해 YOLO 모델의 정확도가 떨어집니다.
+ 횡단보도를 기다릴 때 지나가는 차량에 대한 경고 문구가 계속하여 발생합니다.
+ 거리는 판단 불가능하여 어떤 객체가 더 가까이 있는지 확인할 수 없습니다.(이를 위해 AI-Hub의 Depth Prediction 데이터셋을 활용 가능합니다.)
+ 객체를 동시에 여러 개 인식할 경우 혼란을 방지하기 위해 처음 나온 객체를 기준으로 5초 동안 TTS가 초기화되지 않도록 했으며, 이 때문에 차량의 갑작스러운 보도 침범 시 대처 능력이 떨어집니다.
+ 앱을 사용하는 동안 휴대폰의 다른 기능을 사용하기에 어려움이 있습니다.
+ 이후 인구 혼잡도를 반영한 경로 안내 기능(내비게이션 기능)을 추가하는 방향을 고려하고 있습니다.
