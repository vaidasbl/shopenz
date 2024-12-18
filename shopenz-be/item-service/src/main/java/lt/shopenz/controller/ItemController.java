package lt.shopenz.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.shopenz.model.Item;
import lt.shopenz.service.ItemService;

@RestController
@RequestMapping("/api/item")
public class ItemController
{
    private final ItemService itemService;

    public ItemController(ItemService itemService)
    {
        this.itemService = itemService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public List<Item> getAllItems()
    {
        return itemService.getAllItems();
    }

    @PostMapping("")
    public Item addItem(@RequestBody Item item)
    {
        return itemService.addItem(item);
    }

    @PutMapping("/{itemId}/add/{cartId}")
    public void addItemToCart(@PathVariable Long itemId, @PathVariable Long cartId)
    {
        itemService.addItemToCart(itemId, cartId);
    }

    @PutMapping("/{itemId}/remove/{cartId}")
    public void removeItemFromCart(@PathVariable Long itemId, @PathVariable Long cartId)
    {
        itemService.removeItemFromCart(itemId, cartId);
    }

}
