package com.practice.diary.service;

import com.practice.diary.model.Diary;
import com.practice.diary.repository.DiaryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {

  // DiaryRepository를 주입받음
  private final DiaryRepository diaryRepository;

  // 생성자
  public DiaryService(DiaryRepository diaryRepository) {
    this.diaryRepository = diaryRepository;
  }

  /**
   *  일기장 날짜 데이터만 가져오는 서비스
   * @param diary : 일기
   * @return 일기장 날짜 데이터 모음 리스트
   */
  public List<LocalDateTime> findDatesByUsername(String username) {
    List<Diary> diaries = diaryRepository.findDatesByUsername(username);
    return diaries.stream().map(Diary::getDate).collect(Collectors.toList());
  }

  /**
   *  해당 날짜에 대한 일기 데이터 가져오는 서비스
   * @param username : 사용자 이름
   * @param date : 날짜
   * @return 일기 데이터 리스트
   */
  public List<Diary> findByUsernameAndDate(String username, String date) {
    return diaryRepository.findByUsernameAndDate(username, date);
  }

  /**
   *  일기 데이터 생성 서비스
   * @param title
   * @param content
   * @param date
   * @param emotio
   * @param username
   * @return 저장된 일기
   */
  public Diary createDiary(
    String title,
    String content,
    LocalDateTime date,
    String username,
    String emotion
  ) {
    Diary diary = new Diary();
    diary.setTitle(title);
    diary.setContent(content);
    diary.setDate(date);
    diary.setUsername(username);
    diary.setEmotion(emotion);
    return diaryRepository.save(diary);
  }

  /**
   *  일기 데이터 수정 서비스
   * @param title
   * @param content
   * @param date
   * @param username
   * @param emotion
   * @return 수정된 일기
   */
  public Diary updateDiary(
    String title,
    String content,
    LocalDateTime date,
    String username,
    String emotion
  ) {
    Diary diary = diaryRepository
      .findByUsernameAndDate(username, date.toString())
      .get(0);
    diary.setTitle(title);
    diary.setContent(content);
    diary.setEmotion(emotion);
    return diaryRepository.save(diary);
  }

  /**
   *  일기 데이터 삭제 서비스
   * @param username
   * @param date
   */
  public void deleteDiary(String username, String date) {
    diaryRepository.delete(
      diaryRepository.findByUsernameAndDate(username, date).get(0)
    );
  }
}
