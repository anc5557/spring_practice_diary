package com.practice.diary.repository;

import com.practice.diary.model.Diary;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DiaryRepository extends MongoRepository<Diary, String> {
  List<Diary> findByUsername(String username);

  @Query(value = "{'username': ?0}", fields = "{'date': 1, '_id': 0}")
  List<Diary> findDatesByUsername(String username);

  List<Diary> findByUsernameAndDate(String username, String date);

  
}
