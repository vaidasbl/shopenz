package lt.shopenz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.shopenz.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>
{
}
