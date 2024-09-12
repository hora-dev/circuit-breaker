package com.onebit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ExternalService externalService = new ExternalService();
        CircuitBreaker circuitBreaker = new CircuitBreaker(3, 5000); // Máx 3 fallos, 5 segundos de timeout

        for (int i = 0; i < 100; i++) {
            if (circuitBreaker.allowRequest()) {
                try {
                    String response = externalService.callService();
                    System.out.println("Servicio exitoso: " + response);
                    circuitBreaker.recordSuccess(); // Reiniciar el contador de fallos en caso de éxito
                } catch (Exception e) {
                    System.out.println("Fallo del servicio: " + e.getMessage());
                    circuitBreaker.recordFailure(); // Registrar fallo
                }
            } else {
                System.out.println("Circuito abierto, no se permite la llamada al servicio");
            }

            // Simulación de espera
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}