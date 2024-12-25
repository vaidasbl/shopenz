package lt.shopenz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import lt.shopenz.model.CartItem;

public interface CartItemRepository extends CrudRepository<CartItem, Long>
{
    Optional<CartItem> findByItemIdAndUserId(Long itemId, Long userId);

    List<CartItem> findByUserId(Long userId);
}
