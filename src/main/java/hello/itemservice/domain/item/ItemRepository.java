package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        // store 객체 안에 영향을 주지 않게하기위해 ArrayList에 감싸서 리턴한다.
        return new ArrayList<>(store.values());
    }

    // 정확히하기 위해선 ItemParamDTO 객체를 만들고 로직을 짜는 것이 좋다. 이유는 Item 객체에 있는 Id를 사용하지 않기 때문에
    // 다른 개발자가 코드를 읽는 입장에서 Id를 왜 사용하지않는지 혼란을 줄 수 있기 때문이다.
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
