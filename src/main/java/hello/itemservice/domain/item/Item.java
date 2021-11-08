package hello.itemservice.domain.item;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price;  // wrapping 클래스, 객체이기 때문에 null이 들어갈 수 있다.
    private Integer quantity;

    public Item() {}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
