package com.ucd.keynote.domain.notification;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class NotificationController {
    // SSE 연결된 사용자들을 관리하기 위한 리스트
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // 클라이언트가 이 API를 호출하면 SSE 연결이 수립됨
    @GetMapping(value = "/api/notifications/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L); // 1시간 동안 연결 유지
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter)); // 연결이 완료되면 삭제
        emitter.onTimeout(() -> emitters.remove(emitter)); // 타임아웃 시 삭제
        emitter.onError(e -> emitters.remove(emitter)); // 에러 발생 시 삭제

        return emitter;
    }

    // 알림을 보낼 메소드
    public void sendNotification(String message) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(message));
            } catch (IOException e) {
                emitters.remove(emitter); // 실패한 emitter는 제거
            }
        }
    }
}
