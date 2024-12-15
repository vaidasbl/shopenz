package lt.shopenz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.shopenz.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>
{
    void deleteByUserId(Long userId);
}
