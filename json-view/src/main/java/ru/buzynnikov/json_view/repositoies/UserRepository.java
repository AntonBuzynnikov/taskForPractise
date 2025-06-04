package ru.buzynnikov.json_view.repositoies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.buzynnikov.json_view.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userOrders o WHERE u.id=:id")
    Optional<User> findUserWithoutOrdersById(@Param("id") Long id);

    @Query("""
            SELECT DISTINCT u FROM User u
            LEFT JOIN FETCH u.userOrders o
            LEFT JOIN FETCH o.items oi
            LEFT JOIN FETCH oi.product p
            WHERE u.id = :id
            """)
    Optional<User> findUserWithOrdersById(@Param("id") Long id);

    @Query("""
            SELECT DISTINCT u FROM User u
            LEFT JOIN FETCH u.userOrders o
            LEFT JOIN FETCH o.items oi
            LEFT JOIN FETCH oi.product p
            """)
    Set<User> findAllWithFullData();
}