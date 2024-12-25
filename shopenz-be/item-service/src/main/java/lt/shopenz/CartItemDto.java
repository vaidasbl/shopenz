package lt.shopenz;

import lombok.Data;
import lombok.NoArgsConstructor;
import lt.shopenz.model.CartItem;
import lt.shopenz.model.Item;

@NoArgsConstructor
@Data
public class CartItemDto
{
    private Long id;
    private Long itemId;
    private String name;
    private Long quantity;

    public CartItemDto(Item item, CartItem cartItem)
    {
        this.id = cartItem.getId();
        this.itemId = item.getId();
        this.name = item.getName();
        this.quantity = cartItem.getQuantity();
    }
}
