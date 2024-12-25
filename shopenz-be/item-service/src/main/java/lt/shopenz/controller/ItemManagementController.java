package lt.shopenz.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.shopenz.model.Item;
import lt.shopenz.service.ItemManagementService;

@RestController
@RequestMapping("/api/item")
@PreAuthorize("hasAuthority('ADMIN')")
public class ItemManagementController
{
    private final ItemManagementService itemManagementService;

    public ItemManagementController(final ItemManagementService service)
    {
        this.itemManagementService = service;
    }

    @PostMapping("")
    public Item addItem(@RequestBody Item item)
    {
        return itemManagementService.addItem(item);
    }

    @PutMapping("/{itemId}")
    public void updateItem(@PathVariable final Long itemId, @RequestBody Item item)
    {
        itemManagementService.updateItemById(itemId, item);
    }
}
