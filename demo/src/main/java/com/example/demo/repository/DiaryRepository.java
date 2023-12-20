package com.example.demo.repository;

import com.example.demo.model.Diary;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiaryRepository extends MongoRepository<Diary, Long> {
  // 특정 사용자 ID에 대한 모든 일기 찾기
  List<Diary> findByUserId(Long userId);

  // 특정 감정을 가진 일기 찾기
  List<Diary> findByEmotion(String emotion);

  // 특정 날짜에 작성된 일기 찾기
  List<Diary> findByDate(Date date);
}
