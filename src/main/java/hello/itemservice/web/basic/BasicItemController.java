package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;


    /**
     * 상품 조회 (GET)
     * @param model
     * @return
     */
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    /**
     * 상품 상세 조회(GET)
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/item";
    }

    /**
     * 상품 추가 폼 페이지 이동
     * @return
     */
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * 상품 추가 비즈니스 로직(메서드 파라미터를 여러가지로 사용해본다.)
     * V1 : @RequestParam으로 값을 하나씩 불러와서 item 객체를 생성하고 저장한다음 repository에 저장
     */
    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }


    /**
     * V2 : @ModelAttribute로 데이터값을 가져와서 item 객체에 넣는다.
     * @ModelAttribute에 변수명을 적어주면 데이터를 해당 객체에 넣어주고, model 객체에도 Attribute이름을 해당변수명으로 넣어준다.
     * (Model 객체도 작성 안해도됨)
     */
    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * V3 : Model 객체를 생략한다. 이럴 경우, @ModelAttribute는 자동으로 Model 객체를 생성하고 addAttribute로 ModelAttrubute로
     * 지정한 클래스이름의 첫 글자를 소문자로 바꾼 이름을 넣는다.(코드 작성 생략)
     * V4 : @ModelAttribute도 생략한다. 메서드파라미터에 기본타입이 오면 @RequestParam, 객체가 오면 @ModelAttribute로 자동
     * 적용되어진다. 이 점을 이용하여 애노테이션을 사용하지 않고 Item 객체만 넣어서 사용.
     */
    // @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        // Model 객체에 Item > item 으로 객체가 추가된다.
        // PRG 패턴 사용 : 아이템 상세 목록으로 redirect
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * V5 : redirect를 하는 것 까진 좋다. 하지만 URL에 변수를 연산하여 처리할 경우 인코딩에 문제가 생기면 제대로 데이터가 전송되지 않는
     * 문제점이 있다. 또한 클라이언트의 요구사항에 게시물 저장시 저장되었다는 메시지가 뜨도록 하라는 시나리오가 있어서 구현해본 코드이다.
     */
    @PostMapping("/add")
    public String addItemV5(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);

        // redirect 시 넘어간 데이터를 사용할 수 있는 객체인 RedirectAttributes 생성
        // return 시 redirect: 를 넣는다면 RedirectAttribute에 넣은 key를 사용할 수 있다.
        // 사용하지않은 attribute는 쿼리파라미터로 자동으로 넘어가게 된다.
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 상품 수정 폼 페이지 이동
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long id, Model model) {
        Item findItem = itemRepository.findById(id);
        model.addAttribute(findItem);
        return "basic/editForm";
    }


    /**
     * 상품 수정 버튼 클릭 시 상품 상세페이지로 redirect, 새로고침 중복 수정 방지
     */
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        // redirct: 해당 URL로 redirect로 이동한다. 따라서 새로고침하여 수정을 여러번 요청할 일이 없어진다.
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void inits() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
