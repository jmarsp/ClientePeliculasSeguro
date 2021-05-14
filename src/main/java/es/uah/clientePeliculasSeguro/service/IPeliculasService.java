package es.uah.clientePeliculasSeguro.service;

import es.uah.clientePeliculasSeguro.model.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IPeliculasService {
    /**Esta interfaz declara las operaciones que el servicio puede realizar**
     * Estas operaciones están implementadas en la clase PeliculasServiceImpl
     * Observar la diferencia con la clase IPeliculasService del backend que es añadir un objeto
     * de la clase Pageable para qeu se pueda realizar la paginación en el formulario*/

        Page<Pelicula> buscarTodos(Pageable pageable);

        Pelicula buscarPeliculaPorId(Integer idPelicula);

        Page<Pelicula> buscarPeliculasPorTitulo(String titulo,Pageable pageable);

        Page<Pelicula> buscarPeliculasPorGenero(String genero,Pageable pageable);

        Page<Pelicula> buscarPeliculasPorDirector(String director,Pageable pageable);

        void guardarPelicula(Pelicula pelicula);

        void eliminarPelicula(Integer idPelicula);

        /**
         * La operación actualizarPelicula() no se define porque para actualizar se va a utilizar el método
         * guardarPelicula() que en la clase qeu lo implementa diferencia si se debe guardar o actualizar
         * Así es posible reutilizar el mismo formulario html para ambas cosas.
         *
         *  void actualizarPelicula(Pelicula pelicula);
         **/
    }

