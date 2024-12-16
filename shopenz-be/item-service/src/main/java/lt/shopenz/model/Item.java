package lt.shopenz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long stock;

    public void addToStock()
    {
        this.stock += 1;
    }

    public void removeFromStock()
    {
        if (this.stock > 0)
        {
            this.stock -= 1;
        }
        else
        {
            throw new IllegalStateException("Stock is empty");
        }
    }
}
