package com.calblay.catering_api.menus.application.usecase;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
 

@Data
@AllArgsConstructor
public class MenuListItemDetallado {
 
    private Integer         idMenu;
    private Integer         idCreador;
    private String          nombre;
    private String          imagenUrl;
    private LocalDateTime   fechaCreacion;
    private String          estado;
 
    private Set<String>         alergenosUnicos;
 
    
    private List<Set<String>>   ingredientesPorPlato;
 
    
    private List<Integer>       tiemposPorPlato;
 
     private List<Integer>       categoriaIds;
}