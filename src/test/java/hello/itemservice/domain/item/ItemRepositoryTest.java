package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item savedItem = itemRepository.save(item);  // Repository에 save를 하면 저장한 item 객체를 리턴한다.
        //then
        Item findItem = itemRepository.findById(item.getId()); // id를 통해 아이템 객체를 찾아서 리턴해준다.
        assertThat(findItem).isEqualTo(savedItem); // 저장한 item 객체와 id로 찾은 객체가 같은지 비교
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 5000, 5);
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when : 테스트로 확인하려는 로직을 작성한다. findAll을 체크하므로 findAll만 when에 넣어줌줌
       List<Item> items = itemRepository.findAll();

        //then

        // 내가 작성한 코드 : itemA가 items의 0번 index와 같은지, 1번 index와 같은지를 비교
        // Assertions.assertThat(itemA).isEqualTo(items.get(0));
        // Assertions.assertThat(itemB).isEqualTo(items.get(1));

        // 강사님이 작성한 코드 : items의 길이가 2인지를 확인, itemA와 itemB 객체를 포함하는지 contains() 메서드로 확인
        Assertions.assertThat(items.size()).isEqualTo(2);
        Assertions.assertThat(items).contains(itemA, itemB);
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        Item itemUpdateParam = new Item("itemB", 5000, 5);
        itemRepository.update(itemId, itemUpdateParam);

        //then
        Item findItem = itemRepository.findById(itemId);
        Assertions.assertThat(findItem.getItemName()).isEqualTo(itemUpdateParam.getItemName());
        Assertions.assertThat(findItem.getPrice()).isEqualTo(itemUpdateParam.getPrice());
        Assertions.assertThat(findItem.getQuantity()).isEqualTo(itemUpdateParam.getQuantity());

    }
}