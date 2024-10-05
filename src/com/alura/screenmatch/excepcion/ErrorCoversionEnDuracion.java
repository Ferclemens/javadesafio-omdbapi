package com.alura.screenmatch.excepcion;

public class ErrorCoversionEnDuracion extends RuntimeException {
    private String mensaje;
    public ErrorCoversionEnDuracion(String mensaje) {
        this.mensaje = mensaje;
    }
    @Override
    public String getMessage() {
        return this.mensaje;
    }
}
