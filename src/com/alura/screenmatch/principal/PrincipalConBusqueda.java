package com.alura.screenmatch.principal;

import com.alura.screenmatch.excepcion.ErrorCoversionEnDuracion;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonToken;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalConBusqueda {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        List<Titulo> arrayDePeliculas = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        while (true){
            System.out.println("Escriba el nombre de una pelicula: ");
            var busqueda = lectura.nextLine();
            if(busqueda.equalsIgnoreCase("salir")){
                break;
            }
            String direccion = "https://www.omdbapi.com/?t="+busqueda.replace(" ", "+")+"&apikey=d4d0bf92";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            System.out.println(response.body());


            TituloOmdb miTituloOmb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(miTituloOmb);
            try {
                Titulo miTitulo = new Titulo(miTituloOmb);
                System.out.println(miTitulo);
                arrayDePeliculas.add(miTitulo);
            } catch (NumberFormatException e){
                System.out.println("ocurrio un error");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error en la URI: verifique la dirección");
                System.out.println(e.getMessage());
            } catch (ErrorCoversionEnDuracion e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println(arrayDePeliculas);
        FileWriter escritura = new FileWriter("peliculas.json");
        escritura.write(gson.toJson(arrayDePeliculas));
        escritura.close();
        System.out.println("Fin del programa!");
    }
}
