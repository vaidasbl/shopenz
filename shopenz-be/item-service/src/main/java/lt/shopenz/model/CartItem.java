package lt.shopenz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id")
    private Long itemId;

    private String name;

    private Long quantity;

    @Column(name = "user_id")
    private Long userId;

    public CartItem(Item item, Long userId)
    {
        this.itemId = item.getId();
        this.name = item.getName();
        this.userId = userId;
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
