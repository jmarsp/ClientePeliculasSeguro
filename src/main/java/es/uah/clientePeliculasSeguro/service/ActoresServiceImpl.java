package es.uah.clientePeliculasSeguro.service;

import es.uah.clientePeliculasSeguro.model.Actor;
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
public class ActoresServiceImpl implements IActoresService{

    @Autowired
    RestTemplate template;

    String url = "http://localhost:8001/actores";  /*Direccion base*/

    @Override
    public Page<Actor> buscarTodos(Pageable pageable) {
        Actor[] actores = template.getForObject(url, Actor[].class);
        List<Actor> actoresList = Arrays.asList(actores);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Actor> list;

        if(actoresList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, actoresList.size());
            list = actoresList.subList(startItem, toIndex);
        }
        Page<Actor> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), actoresList.size());
        return page;
    }

    @Override
    public Actor buscarActorPorId(Integer idActor) {
        Actor actor = template.getForObject(url+"/"+idActor, Actor.class);
        return actor;
    }

    @Override
    public Page<Actor> buscarActorPorNombre(String nombre, Pageable pageable) {
        Actor actores = template.getForObject(url+"/nombre/"+nombre, Actor.class);
        List<Actor> lista = Arrays.asList(actores);
        Page<Actor> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }

    @Override
    public void guardarActor(Actor actor) {
        /** Este método actualiza el actor con put si  tiene el idActor > 0
         * es decir, si ya tiened idActor o añade un  nuevo actor si el idActor = 0
         * El formulario de modificar un actor muestra el idActor y el resto de atributos, mientras
         * que el mismo formulario cuando se va a dar de alta un nuevo actor no muestra el idPAcdtor por
         * estar en un campo oculto y ser igual a 0**/
        if (actor.getIdActor() != null && actor.getIdActor() > 0) {
            /** Actualiza el actor*/
            template.put(url, actor);
        } else {
            /** Da de alta un nuevo actor*/
            actor.setIdActor(0);
            template.postForObject(url, actor, String.class);
        }
    }

    @Override
    public void eliminarActor(Integer idActor) {

        template.delete(url + "/" + idActor);
    }

    @Override
    public void inscribirPelicula(Integer idActor, Integer idPelicula) {
        template.getForObject(url+"/insc/"+idActor+"/"+idPelicula, String.class);
    }

}
