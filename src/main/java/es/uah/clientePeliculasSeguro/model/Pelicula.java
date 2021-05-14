package es.uah.clientePeliculasSeguro.model;

import java.util.List;
import java.util.Objects;


public class Pelicula {

    private Integer idPelicula;
    private String titulo;
    private Integer anno;
    private Integer duracion;
    private String director;
    private String genero;
    private String pais;
    private String sinopsis;
    private String imagen;

    private List<Actor> actores; //almacena la lista de actores que trabajan en una película

    public Pelicula(Integer idPelicula, String titulo, Integer anno, Integer duracion, String director, String genero, String pais, String sinopsis,String imagen,List<Actor> actores) {
        this.idPelicula=idPelicula;
        this.titulo=titulo;
        this.anno=anno;
        this.duracion=duracion;
        this.director=director;
        this.genero=genero;
        this.pais=pais;
        this.sinopsis=sinopsis;
        this.imagen=imagen;
        this.actores=actores;
    }

    public Pelicula(){

    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }


    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Actor> getActores() {
        return actores;
    }
    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pelicula)) return false;
        Pelicula pelicula = (Pelicula) o;
        return Objects.equals(idPelicula, pelicula.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPelicula);
    }
    /** A partir de aqui add actor() y remove actor() estaban en el codigo del back . No hacen falta
     * IActoresService tiene métodos para añadir y borrar actores a traves de la API del microservicio back-end
     * como se puede observar en ActoresController

    **/
}
