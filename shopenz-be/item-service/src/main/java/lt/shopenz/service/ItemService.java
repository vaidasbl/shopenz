package lt.shopenz.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lt.shopenz.model.CartItem;
import lt.shopenz.model.Item;
import lt.shopenz.repository.CartItemRepository;
import lt.shopenz.repository.ItemRepository;

@Slf4j
@Service
public class ItemService
{
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    public ItemService(final ItemRepository repository, final CartItemRepository pRepository)
    {
        itemRepository = repository;
        cartItemRepository = pRepository;
    }

    public void addItemToUsersCart(Long itemId, Long userId)
    {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalItem.isEmpty() || optionalItem.get().getStock() < 1)
        {
            throw new NoSuchElementException("Failed to add item to a cart, quantity less than 1");
        }

        Item item = optionalItem.get();

        CartItem cartItem = cartItemRepository.findByItemIdAndUserId(itemId, userId).orElse(new CartItem(item, userId));

        cartItem.addToQuantity();
        item.removeFromStock();

        itemRepository.save(item);
        cartItemRepository.save(cartItem);
    }

    public void removeItemFromUsersCart(Long itemId, Long userId)
    {
        CartItem cartItem = cartItemRepository.findByItemIdAndUserId(itemId, userId)
                .orElseThrow(() -> new NoSuchElementException("CartItem not found. ItemId: " + itemId + ", UserId: " + userId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found. ItemId: " + itemId));

        cartItem.removeFromQuantity();

        item.addToStock();
        itemRepository.save(item);

        if (cartItem.getQuantity() == 0)
        {
            cartItemRepository.delete(cartItem);
        }
        else
        {
            cartItemRepository.save(cartItem);
        }
    }

    public List<CartItem> getUsersCartItems(Long userId)
    {
        return cartItemRepository.findByUserId(userId);
    }
}
