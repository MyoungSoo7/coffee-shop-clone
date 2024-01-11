package com.lms.coffeeshopclone.menu.domain;


import com.lms.coffeeshopclone.menu.application.MenuDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`menu`")
@Data
// 외부에서 생성불가, 상속클래스에서만 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private String menuName;

    private Long menuPrice;

    public Menu(String menuName, Long menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public MenuDto toDto() {
        return new MenuDto(menuId, menuName, menuPrice);
    }


}
