package es.uah.clientePeliculasSeguro.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;

public class Actor{

    private Integer idActor;
    private String nombre;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es-ES", timezone = "Europe/Madrid")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaNacimiento;
    private String paisNacimiento;
    private List<Pelicula> peliculas;

    public Actor(String nombre,Date fechaNacimiento,String paisNacimiento){
        this.idActor=0;
        this.nombre=nombre;
        this.fechaNacimiento=fechaNacimiento;
        this.paisNacimiento=paisNacimiento;
        this.peliculas=new ArrayList<>();

    }

    public Actor() {
    }

    public Integer getIdActor() {
        return idActor;
    }
    public void setIdActor(Integer idActor) {
        this.idActor = idActor;
    }


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    public String getPaisNacimiento() {
        return paisNacimiento;
    }
    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public List <Pelicula> getPeliculas(){
        return peliculas;
    }
    public void setPeliculas (List <Pelicula> peliculas){
        this.peliculas = peliculas;
    }

/** A partir de aqui add pelicula() y remove pelicula() estaban en el codigo del back borrarlos. No hacen falta
 * IPeliculasService tiene métodos para añandir y borrar películas a traves de la API del microservicio back-end
 *      * como se puede observar en PeliculasController


 **/
}