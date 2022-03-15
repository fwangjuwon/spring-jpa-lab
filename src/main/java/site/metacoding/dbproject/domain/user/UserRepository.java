package site.metacoding.dbproject.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // 레파지토리한테는 얘를 붙여서 ioc container에 올리자 -> 사실 안 붙여도 된다. 부모클래스에 정의되어 있다.
public interface UserRepository extends JpaRepository<User, Integer> {

    // jparepository에 없는 것은 직접 만들기 (복잡한 거) -> 앞에 m붙여서 분리
    @Query(value = "SELECT * FROM user WHERE username = :username AND password = :password", nativeQuery = true)
    User mLogin(@Param("username") String username, @Param("password") String password); // 영속성 컨텍스트가 해주는것

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User mUsernameSameCehck(@Param("username") String username); // 영속성 컨텍스트가 해주는것

    // find All()
    // SELECT * FROM user; 쿼리가 자동으로 만들어진다. (jparepository에 의해서)

    // findById()
    // SELECT * FROM user WHERE id =?

    // save()
    // INSERT INTO user(username, password, email, createDate) VALUES(?,?,?,?)

    // deleteById()
    // DELETE FROM user WHERE id=?

    // update는 없다 -> 영속성 context공부 후 사용가능
}
