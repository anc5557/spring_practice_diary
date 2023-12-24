package com.practice.diary.controller;

import com.practice.diary.model.Diary;
import com.practice.diary.service.DiaryService;
import com.practice.diary.util.TokenUtils;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diary")
public class DiaryController {

  private final DiaryService diaryService;
  private final TokenUtils tokenUtils;

  public DiaryController(DiaryService diaryService, TokenUtils tokenUtils) {
    this.diaryService = diaryService;
    this.tokenUtils = tokenUtils;
  }

  // 일기장 날짜 데이터 가져오기
  @GetMapping("/dates")
  public ResponseEntity<List<LocalDateTime>> getDiaryDates(
    @RequestHeader("Authorization") String header
  ) {
    String username = tokenUtils.getUsernameFromToken(header);
    return ResponseEntity.ok(diaryService.findDatesByUsername(username));
  }

  // 해당 날짜에 대한 일기 데이터 가져오기
  @GetMapping("/{date}")
  public ResponseEntity<List<Diary>> getDiaryByDate(
    @PathVariable String date,
    @RequestHeader("Authorization") String header
  ) {
    String username = tokenUtils.getUsernameFromToken(header);
    return ResponseEntity.ok(
      diaryService.findByUsernameAndDate(username, date)
    );
  }

  // 일기 데이터 생성
  @PostMapping("/")
  public ResponseEntity<Diary> createDiary(
    @RequestBody Diary diary,
    @RequestHeader("Authorization") String header
  ) {
    String username = tokenUtils.getUsernameFromToken(header);
    diary.setUsername(username);
    return ResponseEntity.ok(
      diaryService.createDiary(
        diary.getTitle(),
        diary.getContent(),
        diary.getDate(),
        username,
        diary.getEmotion()
      )
    );
  }

  // 일기 데이터 업데이트
  @PutMapping("/{date}")
  public ResponseEntity<Diary> updateDiary(
    @PathVariable LocalDateTime date,
    @RequestBody Diary updatedDiary,
    @RequestHeader("Authorization") String header
  ) {
    String username = tokenUtils.getUsernameFromToken(header);
    updatedDiary.setUsername(username);
    return ResponseEntity.ok(
      diaryService.updateDiary(
        updatedDiary.getTitle(),
        updatedDiary.getContent(),
        date,
        username,
        updatedDiary.getEmotion()
      )
    );
  }

  // 일기 데이터 삭제
  @DeleteMapping("/{date}")
  public ResponseEntity<Void> deleteDiary(
    @PathVariable String date,
    @RequestHeader("Authorization") String header
  ) {
    String username = tokenUtils.getUsernameFromToken(header);
    diaryService.deleteDiary(username, date);
    return ResponseEntity.ok().build();
  }
}
