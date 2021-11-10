# SpringBoot_CRUD
스프링부트로 간단한 CRUD 구현하기

사용한 기술
- Spring Boot 2.5.x
- ThymeLeaf
- lombok
- Junit5
- DB는 따로 사용하지않았습니다.

## 구현해본 기능들

### SpringBoot(Controller)
- @RequestParam, @ModelAttribute 애노테이션을 사용하여 요청파라미터 받아오기
- GetMapping, PostMapping 방식을 통해 같은 URL이지만 다른 비즈니스 로직이 작동하도록 구현
- PathVariable을 통해 아이템을 조회, 수정, 삭제가 가능하도록 구현
- RedirectAttribute 객체를 사용하여 Redirect시 쿼리파라미터를 전달
- PRG(Post Redirect Get)방식으로 아이템 추가 시 중복요청이 처리되지 않도록 구현

### ThymeLeaf(Template Engine)
- th:xxx 문법을 사용하여 정적 HTML이 동적으로 데이터를 가져올 수 있도록 구현
- 리터럴을 사용하여 문자열에 '+' 연산을 하지 않고 코드작성

### Junit
- DB를 사용하지않고 메모리 객체를 사용하여 임시로 데이터를 저장하게끔 itemRepository 객체 생성
- itemRepository에 구현한 메소드인 save, findAll, update 메서드가 잘 작동하는지 테스트 코드 작성
