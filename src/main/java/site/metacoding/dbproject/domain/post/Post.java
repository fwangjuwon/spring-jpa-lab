package site.metacoding.dbproject.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import site.metacoding.dbproject.domain.user.User;

@Entity
public class Post {

    @Id // 얘를프라이머리키로 만들어 준다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // primary key

    @Column(length = 300, nullable = false)
    private String title; // won5354 id

    @Lob // clob 4gb 문자타입
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne // n:1 에서 n에 포린키 post:user = n:1
    private User user;

    private LocalDateTime createDate;
}
