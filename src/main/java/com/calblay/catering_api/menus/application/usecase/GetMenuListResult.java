package com.calblay.catering_api.menus.application.usecase;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
 
// Envuelve la lista de menús que devuelve el caso de uso GetMenuListUseCase
@Data
@AllArgsConstructor
public class GetMenuListResult {
    private List<MenuListItem> menus;
}
