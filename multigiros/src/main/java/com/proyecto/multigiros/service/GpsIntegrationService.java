package com.proyecto.multigiros.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.multigiros.entity.MonitoreoGps;
import com.proyecto.multigiros.repository.DispositivoGpsRepository;
import com.proyecto.multigiros.repository.MonitoreoGpsRepository;

@RestController
@RequestMapping("/gps")
@CrossOrigin
public class GpsIntegrationService {
    @Autowired
    private MonitoreoGpsRepository monitoreoGpsRepository;
    
    @Autowired
    private DispositivoGpsRepository dispositivoGpsRepository;
    
    // Endpoint para recibir datos del proveedor GPS
    @PostMapping("/webhook")
    public Map<String, Object> recibirDatosGps(@RequestBody Map<String, Object> datosGps) {
        Map<String, Object> respuesta = new HashMap<>();
        
        try {
            // Procesar datos del GPS
            String codigoDispositivo = (String) datosGps.get("device_id");
            Double latitud = (Double) datosGps.get("latitude");
            Double longitud = (Double) datosGps.get("longitude");
            Double velocidad = (Double) datosGps.get("speed");
            
            // Buscar dispositivo
            var dispositivoOpt = dispositivoGpsRepository.findByCodigoDispositivo(codigoDispositivo);
            
            if (dispositivoOpt.isPresent()) {
                MonitoreoGps monitoreo = new MonitoreoGps();
                monitoreo.setDispositivo(dispositivoOpt.get());
                monitoreo.setLatitud(java.math.BigDecimal.valueOf(latitud));
                monitoreo.setLongitud(java.math.BigDecimal.valueOf(longitud));
                monitoreo.setVelocidad(java.math.BigDecimal.valueOf(velocidad));
                
                monitoreoGpsRepository.save(monitoreo);
                
                respuesta.put("success", true);
                respuesta.put("message", "Datos GPS registrados correctamente");
            } else {
                respuesta.put("success", false);
                respuesta.put("message", "Dispositivo no encontrado");
            }
            
        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("message", "Error procesando datos GPS: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    @GetMapping("/ubicacion/{idDispositivo}")
    public List<MonitoreoGps> obtenerUbicaciones(@PathVariable Long idDispositivo,
                                                @RequestParam String fechaInicio,
                                                @RequestParam String fechaFin) {
        // Implementar lógica para obtener ubicaciones en un rango de fechas
        return monitoreoGpsRepository.findByDispositivoIdDispositivo(idDispositivo);
    }
}
