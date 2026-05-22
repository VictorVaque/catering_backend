package com.calblay.catering_api.menus.application.usecase;
 
import org.springframework.stereotype.Service;
import java.util.*;
 

@Service
public class MenuHeuristicaService {
 
    private static final double PESO_X = 0.35;
    private static final double PESO_Y = 0.40;
    private static final double PESO_Z = 0.25;
 
    
    public List<MenuListItemDetallado> ordenar(List<MenuListItemDetallado> menus) {
        if (menus == null || menus.size() <= 1) return menus;
 
        // 1. Calcular valores crudos para cada dimensión
        double[] rawX = new double[menus.size()];
        double[] rawY = new double[menus.size()];
        double[] rawZ = new double[menus.size()];
 
        for (int i = 0; i < menus.size(); i++) {
            rawX[i] = calcX(menus.get(i));
            rawY[i] = calcY(menus.get(i));
            rawZ[i] = calcZ(menus.get(i));
        }
 
        // 2. Normalizar y ponderar
        double[] puntuacion = new double[menus.size()];
        for (int i = 0; i < menus.size(); i++) {
            double scoreX = normalizar(rawX[i], min(rawX), max(rawX), true);  // invertido
            double scoreY = normalizar(rawY[i], min(rawY), max(rawY), false);
            double scoreZ = normalizar(rawZ[i], min(rawZ), max(rawZ), true);  // invertido
 
            puntuacion[i] = PESO_X * scoreX + PESO_Y * scoreY + PESO_Z * scoreZ;
        }
 
        // 3. Ordenar índices de mayor a menor puntuación
        Integer[] indices = new Integer[menus.size()];
        for (int i = 0; i < indices.length; i++) indices[i] = i;
        Arrays.sort(indices, (a, b) -> Double.compare(puntuacion[b], puntuacion[a]));
 
        List<MenuListItemDetallado> resultado = new ArrayList<>(menus.size());
        for (int idx : indices) resultado.add(menus.get(idx));
        return resultado;
    }
 
    private double calcX(MenuListItemDetallado m) {
        return m.getAlergenosUnicos() == null ? 0 : m.getAlergenosUnicos().size();
    }
 
    
    private double calcY(MenuListItemDetallado m) {
        List<Set<String>> ings = m.getIngredientesPorPlato();
        if (ings == null || ings.size() < 2) return 0;
 
        double total = 0;
        int pares = 0;
 
        for (int i = 0; i < ings.size(); i++) {
            for (int j = i + 1; j < ings.size(); j++) {
                Set<String> interseccion = new HashSet<>(ings.get(i));
                interseccion.retainAll(ings.get(j));
 
                Set<String> union = new HashSet<>(ings.get(i));
                union.addAll(ings.get(j));
 
                total += union.isEmpty() ? 0.0 : (double) interseccion.size() / union.size();
                pares++;
            }
        }
        return pares == 0 ? 0 : total / pares;
    }
 
    
    private double calcZ(MenuListItemDetallado m) {
        List<Integer> tiempos = m.getTiemposPorPlato();
        if (tiempos == null || tiempos.size() < 2) return 0;
 
        double media = tiempos.stream().mapToInt(Integer::intValue).average().orElse(0);
        double varianza = tiempos.stream()
                .mapToDouble(t -> Math.pow(t - media, 2))
                .average()
                .orElse(0);
        return Math.sqrt(varianza);
    }
 
    
    
    private double normalizar(double valor, double min, double max, boolean invertir) {
        if (max == min) return 1.0; 
        double norm = (valor - min) / (max - min);
        return invertir ? 1.0 - norm : norm;
    }
 
    private double min(double[] arr) {
        double m = arr[0];
        for (double v : arr) if (v < m) m = v;
        return m;
    }
 
    private double max(double[] arr) {
        double m = arr[0];
        for (double v : arr) if (v > m) m = v;
        return m;
    }
}
