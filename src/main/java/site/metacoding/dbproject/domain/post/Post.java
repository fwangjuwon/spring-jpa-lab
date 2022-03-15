package site.metacoding.dbproject.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.dbproject.domain.user.User;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
    @ManyToOne(fetch = FetchType.EAGER) // n:1 에서 n에 포린키 post:user = n:1
    private User user;

    @CreatedDate // insert
    private LocalDateTime createDate;

    @LastModifiedDate // insert, update
    private LocalDateTime updateDate;

}
