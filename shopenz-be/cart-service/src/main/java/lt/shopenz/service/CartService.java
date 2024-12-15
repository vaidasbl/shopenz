package lt.shopenz.service;

import org.springframework.stereotype.Service;

import lt.shopenz.model.Cart;
import lt.shopenz.repository.CartRepository;

@Service
public class CartService
{
    private final CartRepository cartRepository;

    public CartService(final CartRepository pRepository)
    {
        cartRepository = pRepository;
    }

    public void createCartForUserId(Long userId)
    {
        Cart cart = new Cart(userId);

        cartRepository.save(cart);
    }

    public void deleteCartByUserId(Long userId)
    {
        cartRepository.deleteByUserId(userId);
    }
}
