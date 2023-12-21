package com.practice.diary.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// 몽고 DB User 모델
@Data
@Entity
@Table(name = "users")
public class User {}
