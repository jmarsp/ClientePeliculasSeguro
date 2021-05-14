package es.uah.clientePeliculasSeguro.service;

import es.uah.clientePeliculasSeguro.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActoresService {
    /**Esta interfaz declara las operaciones que el servicio puede realizar**
     * Estas operaciones están implementadas en la clase ActoresServiceImpl
     * Observar la diferencia con la clase IActoresService del backend que es añadir un objeto
     * de la clase Pageable para qeu se pueda realizar la paginación en el formulario*/
    Page<Actor> buscarTodos(Pageable pageable);
    Actor buscarActorPorId(Integer idActor);
    Page <Actor> buscarActorPorNombre(String nombre,Pageable pageable);
    void guardarActor(Actor actor);
    void eliminarActor(Integer idActor);
    void inscribirPelicula(Integer idActor, Integer idPelicula);

    /**
     * La operación actualizarActor) no se define porque para actualizar se va a utilizar el método
     * guardarActor() que en la clase qeu lo implementa diferencia si se debe guardar o actualizar
     * Así es posible reutilizar el mismo formulario html para ambas cosas.
     *
     *  void actualizarActor(Actor actor);
     **/
}
