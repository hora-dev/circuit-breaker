package com.onebit;

public class ExternalService {

    public String callService() throws Exception {
        // Simulación de un fallo aleatorio en el servicio externo
        if (Math.random() > 0.5) {
            throw new Exception("Error en el servicio");
        }
        return "Respuesta del servicio";
    }

}
