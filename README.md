# kakc

kakc는 Team-MS-2에서 사용하는 경량 유틸리티/테스트 성격의 공개 저장소입니다.

이 문서는 GitHub 기본 브랜치의 메타데이터와 서버 운영 맥락을 기준으로 작성된 한국어 README입니다.

## 저장소 요약

| 항목 | 값 |
| --- | --- |
| GitHub | `Team-MS-2/kakc` |
| 기본 브랜치 | `main` |
| 유형 | Paper/Bukkit 플러그인 |
| Bukkit/Paper 플러그인 | `KAKC` |
| 메인 클래스 | `me.desktop.KAKC.Main` |

## 저장소 구조

- `gradle/`
- `src/`

## 런타임 메타데이터

- 플러그인 descriptor: `src/main/resources/plugin.yml`
- 명령어: `/KAKC`

## 빌드

Gradle wrapper가 있는 저장소는 wrapper를 우선 사용합니다.

```powershell
.\gradlew.bat build
```

## 배포 메모

- 서버/프록시에 배포하기 전에 대상 모듈 jar를 빌드합니다.
- Paper, Bukkit, Velocity, Minecraft 서버 클래스는 런타임이 제공하는 compile-only 의존성으로 유지하는 것을 기본 원칙으로 합니다.
- NMS, Mixin, Horizon, plugin descriptor, classloader 경계를 건드린 경우 서버 재시작으로 검증합니다.

## 유지보수 체크리스트

- 기본 브랜치가 실제 운영/마이그레이션 브랜치와 다른지 먼저 확인합니다.
- 코드 변경 후에는 가장 좁은 유효 빌드 또는 테스트 명령을 실행합니다.
- 모듈명, 런타임 의존성, 배포 파일명이 바뀌면 이 README도 함께 갱신합니다.
