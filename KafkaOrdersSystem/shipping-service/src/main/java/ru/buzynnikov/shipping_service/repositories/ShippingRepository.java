package ru.buzynnikov.shipping_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.shipping_service.models.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
}
