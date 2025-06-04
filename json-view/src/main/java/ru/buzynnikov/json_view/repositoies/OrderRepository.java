package ru.buzynnikov.json_view.repositoies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.json_view.models.UserOrder;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {

    @Query("""
            SELECT DISTINCT o FROM UserOrder o
            LEFT JOIN FETCH o.user u
            LEFT JOIN FETCH o.items oi
            LEFT JOIN FETCH oi.product p
            WHERE o.id=:id
            """)
    Optional<UserOrder> findOrderWithAllDataById(@Param("id") Long id);
}