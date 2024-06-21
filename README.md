# OpenSourceProgramming final project
---
## 시각 장애인을 위한 보행 안내서비스, 시야
object detection deeplearning model인 YOLO(You Only Look Once) version 8을 사용하여 시각장애인이 보행 중 위협 요소가 되는 각종 장애물을 피할 수 있도록 돕는 Android 기반 애플리케이션입니다.
---
## 목차
+ 프로젝트 소개
+ 프로그램 설명
+ YOLOv8을 활용한 모델과 UI 설명
+ 코드 설명
+ 한계점과 보안
---
## 프로젝트 소개
+ 숙명여자대학교 IT공학전공(현 인공지능공학부) 오픈소스프로그래밍 수업의 기말 팀 프로젝트
+ 팀원: 김민지, 김진솔, 박세희
+ 개발 기간: 2024.05.19 ~ 2024.06.20
+ 주요 기능: 장애물 인식 후 TTS 및 진동으로 회피 방향 안내(단, 진동 모드는 화면 왼쪽을 터치하려 ON 시켜야 햐며, 진동 모드 ON시 TTS와 함께 진동 1번(왼쪽 회피)/2번(오른쪽 회피) 울림)
+ 사용 툴: Android Studio Jellyfish, Colab, GitHub
+ 설치 및 실행 방법: Android Sudio를 설치하고 Code-clone한 후 Front-end/AndroidProject를 실행합니다. 안드로이드 기반 스마트폰의 설정-휴대전화 정보-소프트웨어 정보에서 빌드 번호 5번을 누르면 개발자 옵션이 생기는데, 이 옵션에서 USB 디버깅을 켜고 휴대폰과 컴퓨터를 USB 선으로 연결합니다. Android Studio에서 run을 누르면 휴대폰에서 앱이 실행됩니다.
+ 라이선스: MIT license
