package site.metacoding.dbproject.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.servlet.FlashMapManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

//JPA library 는 java(자바 언어로) persistence(영구적인 저장) API (노출되어있는 메소드)
//1. CRUD 메소드를 기본 제공
//2. JAVA code로 db를 자동 생성 기능 제공 -> 설정 ->  application.yml
//3. ORM 제공 -> 이부분은 지금은 몰라도 된다. orm (object relation mapping)

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // server실행 시 해당 클래스로 db 테이블을 생성해! 라고 하는 어노테이션
@EntityListeners(AuditingEntityListener.class) // 현재 시간 입력을 위해 필요한 어노테이션
public class User {

    // identity 전략은 db에게 번호증가 전략을 위임하는 것이다. -> 알아서 db에 맞게 찾아준다.
    @Id // 얘를프라이머리키로 만들어 준다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // primary key

    @Column(length = 20, unique = true)
    private String username; // won5354 id

    @Column(length = 12, nullable = false)
    private String password;

    @Column(length = 16000000)
    private String email;

    @CreatedDate // insert
    private LocalDateTime createDate;

    @LastModifiedDate // insert, update
    private LocalDateTime updateDate;
}
