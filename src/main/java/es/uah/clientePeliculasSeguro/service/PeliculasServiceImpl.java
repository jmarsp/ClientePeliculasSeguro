package es.uah.clientePeliculasSeguro.service;
import es.uah.clientePeliculasSeguro.model.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PeliculasServiceImpl implements IPeliculasService {
    /** Implementa toda la lógica de negocio de los métodos de  la interfaz IPeliculasService
     haciendo uso de los métodos de la interfaz RestTemplate.**/

    @Autowired
    RestTemplate template;

    String url = "http://localhost:8001/peliculas";  /*Direccion base*/

    @Override
    public Page<Pelicula> buscarTodos(Pageable pageable) {
        Pelicula[] peliculas = template.getForObject(url, Pelicula[].class);
        List<Pelicula> peliculasList = Arrays.asList(peliculas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Pelicula> list;

        if (peliculasList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, peliculasList.size());
            list = peliculasList.subList(startItem, toIndex);
        }

        Page<Pelicula> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), peliculasList.size());
        return page;
    }

    @Override
    public Pelicula buscarPeliculaPorId(Integer idPelicula) {
        Pelicula pelicula = template.getForObject(url + "/" + idPelicula, Pelicula.class);
        return pelicula;
    }
    @Override
    public Page<Pelicula> buscarPeliculasPorTitulo(String titulo, Pageable pageable) {
        Pelicula[] peliculas = template.getForObject(url + "/titulo/" + titulo, Pelicula[].class);
        List<Pelicula> lista = Arrays.asList(peliculas);
        Page<Pelicula> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }
    @Override
    public Page<Pelicula> buscarPeliculasPorGenero(String genero, Pageable pageable) {
        Pelicula[] peliculas = template.getForObject(url + "/genero/" + genero, Pelicula[].class);
        List<Pelicula> lista = Arrays.asList(peliculas);
        Page<Pelicula> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }
    @Override
    public Page<Pelicula> buscarPeliculasPorDirector(String director,Pageable pageable) {
        Pelicula[] peliculas = template.getForObject(url + "/director/" + director, Pelicula[].class);
        List<Pelicula> lista = Arrays.asList(peliculas);
        Page<Pelicula> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }
    @Override
    public void guardarPelicula(Pelicula pelicula) {
        /** Este método actualiza la película con put si la película tiene el idPelicula > 0
         * es decir, si ya tiened idPelicula o añade una  nueva película si el idPelicula = 0
         * El formulario de modificar una pelicula muestra el idPelicula y el resto de atributos mientras
         * que el mismo formulario cuando se va a dar de alta una nueva película no muestra el idPelicula por
         * estar en un campo oculto y ser igual a 0**/
        if (pelicula.getIdPelicula() != null && pelicula.getIdPelicula() > 0) {
            /**actualiza la pelicula*/
            template.put(url, pelicula);
        } else {
            /**da de alta la película*/
            pelicula.setIdPelicula(0);
            template.postForObject(url, pelicula, String.class);
        }
    }


    @Override
    public void eliminarPelicula(Integer idPelicula) {
        template.delete(url + "/" + idPelicula);
    }


}
