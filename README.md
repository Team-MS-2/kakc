# KAKC

**Kipa00's Automatic Korean Changer** — 영문 키 입력을 자동으로 한글로 변환해주는 Bukkit/Spigot 플러그인.

원작자: kipa00 (`me.desktop.KAKC`, v1.2). 본 저장소는 `kakc.jar`를 디컴파일·복원한 Gradle 프로젝트.

## 무엇을 하는가

Minecraft에서 한/영 전환 없이 한글로 입력하다 보면 종종 `gksrmf`(→ `한글`)처럼 영문이 그대로 채팅에 찍히는 경험을 한다. KAKC는 이런 입력을 서버 단에서 가로채 두벌식 자판 매핑에 따라 한글 음절로 재조합해준다.

대상 이벤트:
- `AsyncPlayerChatEvent` — 일반 채팅
- `PlayerCommandPreprocessEvent` — `/명령어` 인자
- `PlayerJoinEvent` — 입장 시 기본 모드(1) 설정 및 환영 메시지

## 동작 방식

`HangeulChanger.translate()`가 핵심 변환기다. 입력 문자열을 한 글자씩 버퍼에 넣고 `needSeperating()`으로 음절 경계를 판정한 뒤, `assemble()`로 초성·중성·종성을 합쳐 유니코드 한글(`U+AC00` 시작) 코드포인트를 만든다.

매핑은 두벌식 표준이며 코드 안에 문자열 테이블로 박혀있다 (예: 자음 `rRseEfaqQtTdwWczxvg` → `ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ`).

`changeInAdvancedHangeul()`은 변환 흐름을 사용자 친화적으로 감싼다:
- 한/영 전환 키(기본 `"`)를 만나면 토글
- `&` 컬러 코드는 통과
- `\\`는 escape (한 번 더 쓰면 리터럴 `\\` 출력)

## 명령어

```
/KAKC                      도움말
/KAKC help [sub]           서브 명령 도움말
/KAKC changemod | chmod  N 변환 모드 설정 (0=끔, 1=ASCII만, 2=항상)
/KAKC changekey | chkey  C 한/영 전환 키 설정 (특수문자 1글자, '&'·'\\' 제외)
/KAKC howto                사용 예시
```

기본값: `mod=1`, `changekey="`.

## 빌드

```powershell
./gradlew build
```

요구사항:
- JDK 21 (Gradle toolchain이 자동으로 잡음)
- Gradle 8.14.3 (wrapper 포함)

산출물: `build/libs/kakc.jar` — 그대로 서버 `plugins/`에 넣으면 됨.

의존성: `org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT` (compileOnly).

## 프로젝트 구조

```
src/main/java/me/desktop/KAKC/
├── Main.java            JavaPlugin, 이벤트/명령 처리
├── HangeulChanger.java  두벌식 → 한글 변환 엔진
└── CharType.java        ASCII/AlphaNum 판별 유틸
src/main/resources/
└── plugin.yml
```

## 비고

원본 클래스 파일은 Java 6 (major 50)으로 컴파일돼 있었으나, 본 복원본은 Java 21로 다시 빌드한다. 디컴파일러가 만들어낸 `var5` 같은 아티팩트는 정리했고 `Map` 등에는 제네릭을 보강했지만 로직 자체는 그대로 보존했다.
