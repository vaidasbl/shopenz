package lt.shopenz.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.shopenz.model.Item;
import lt.shopenz.repository.ItemRepository;

@Service
public class ItemManagementService
{
    private final ItemRepository itemRepository;

    @Autowired
    public ItemManagementService(final ItemRepository repository)
    {
        itemRepository = repository;
    }

    public List<Item> getAllItems()
    {
        return itemRepository.findAll();
    }

    public Item addItem(Item item)
    {
        return itemRepository.save(item);
    }

    public void updateItemById(Long itemId, Item updatedItem)
    {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalItem.isEmpty())
        {
            throw new NoSuchElementException(String.format("Item with id '%s' was not found", itemId));
        }

        Item item = optionalItem.get();
        item.setName(updatedItem.getName());
        item.setStock(updatedItem.getStock());

        itemRepository.save(item);
    }

}
