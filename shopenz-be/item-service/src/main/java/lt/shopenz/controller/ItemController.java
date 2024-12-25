package lt.shopenz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lt.shopenz.JwtUtils;
import lt.shopenz.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController
{
    private final ItemService itemService;

    public ItemController(ItemService itemService)
    {
        this.itemService = itemService;
    }

    @PutMapping("/{itemId}/add/{userId}")
    public void addItemToCart(@PathVariable Long itemId, @PathVariable Long userId)
    {
        itemService.addItemToUsersCart(itemId, userId);
    }

    @PutMapping("/{itemId}/remove/{userId}")
    public void removeItemFromCart(@PathVariable Long itemId, @PathVariable Long userId)
    {
        itemService.removeItemFromUsersCart(itemId, userId);
    }

    @GetMapping("/carts/{userId}")
    public ResponseEntity<?> getUsersCartItems(HttpServletRequest request, @PathVariable Long userId)
    {
        Long requesterId = JwtUtils.getUserIdFromRequest(request);

        if (requesterId == null || !requesterId.equals(userId))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You dont have permission to access this cart items");
        }

        return ResponseEntity.ok().body(itemService.getUsersCartItems(userId));
    }

}
