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

    public List<Item> getAllItems()
    {
        return itemRepository.findAll();
    }

    public Item addItem(Item item)
    {
        return itemRepository.save(item);
    }

    public void addItemToCart(Long itemId, Long cartId)
    {
        Optional<Item> item = itemRepository.findById(itemId);

        if (item.isEmpty() || item.get().getStock() < 1)
        {
            throw new NoSuchElementException("Failed to add item to a cart, quantity less than 1");
        }

        CartItem cartItem = cartItemRepository.findByItemIdAndCartId(itemId, cartId).orElse(new CartItem(itemId, cartId));

        cartItem.addToQuantity();
        item.get().removeFromStock();

        itemRepository.save(item.get());
        cartItemRepository.save(cartItem);
    }

    public void removeItemFromCart(Long itemId, Long cartId)
    {
        CartItem cartItem = cartItemRepository.findByItemIdAndCartId(itemId, cartId)
                .orElseThrow(() -> new NoSuchElementException("CartItem not found. ItemId: " + itemId + ", CartId: " + cartId));

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
}
