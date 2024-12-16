package lt.shopenz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem
{
    @Id
    @Column(name = "item_id")
    private Long itemId;
    private Long quantity;

    @Column(name = "cart_id")
    private Long cartId;

    public CartItem(Long itemId, Long cartId)
    {
        this.itemId = itemId;
        this.cartId = cartId;
        this.quantity = 0L;
    }

    public void addToQuantity()
    {
        this.quantity += 1;
    }

    public void removeFromQuantity()
    {
        if (this.quantity > 0)
        {
            this.quantity -= 1;
        }
    }
}
