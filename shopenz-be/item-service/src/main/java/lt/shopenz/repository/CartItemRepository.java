package lt.shopenz.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import lt.shopenz.model.CartItem;

public interface CartItemRepository extends CrudRepository<CartItem, Long>
{
    Optional<CartItem> findByItemIdAndCartId(Long itemId, Long cartId);
}
