package com.calblay.catering_api.menus.application.usecase;
 
import com.calblay.catering_api.menus.application.port.out.GetMenuListPort;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
public class GetMenuListUseCase {
 
    private final GetMenuListPort       getMenuListPort;
    private final MenuHeuristicaService heuristicaService;
 
    public GetMenuListUseCase(GetMenuListPort getMenuListPort,
                              MenuHeuristicaService heuristicaService) {
        this.getMenuListPort   = getMenuListPort;
        this.heuristicaService = heuristicaService;
    }
 
    public GetMenuListResult getMenuList(String searchNombre,
                                        List<String> sinAlergenos,
                                        List<Integer> categoriaIds,
                                        List<String> etiquetas) {
 
        List<MenuListItemDetallado> detallados = getMenuListPort.findMenusParaHeuristica(
                searchNombre, sinAlergenos, categoriaIds, etiquetas);
 
        List<MenuListItemDetallado> ordenados = heuristicaService.ordenar(detallados);
 
        List<MenuListItem> items = ordenados.stream()
                .map(m -> new MenuListItem(
                        m.getIdMenu(),
                        m.getIdCreador(),
                        m.getNombre(),
                        m.getImagenUrl(),
                        m.getFechaCreacion(),
                        m.getEstado()
                ))
                .collect(Collectors.toList());
 
        return new GetMenuListResult(items);
    }
}
