package es.uah.clientePeliculasSeguro.controller;

import es.uah.clientePeliculasSeguro.model.Actor;
import es.uah.clientePeliculasSeguro.model.Pelicula;
import es.uah.clientePeliculasSeguro.paginator.PageRender;
import es.uah.clientePeliculasSeguro.service.IActoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**import java.security.Principal; Utilizada para versión Trabajo Final**/
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/cactores") /**Establece la direccion base del lado del cliente*/
public class ActoresController {
    @Autowired
    IActoresService actoresService;

    @GetMapping("/listado")
    public String listadoActores(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> listado = actoresService.buscarTodos(pageable);
        PageRender<Actor> pageRender = new PageRender<Actor>("/cactores/listado", listado);
        model.addAttribute("titulo", "Listado de todos los actores");
        model.addAttribute("listadoActores", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listActor";/** llama al formulario listActor.hmtl**/
    }

    @GetMapping("/actor")
    public String buscarActoresPorNombre(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("nombre") String nombre) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> listado = actoresService.buscarActorPorNombre (nombre, pageable);
        PageRender<Actor> pageRender = new PageRender<Actor>("/listado", listado);
        model.addAttribute("titulo", "Listado de Películas por Actor");
        model.addAttribute("listadoActores", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listActor"; /** llama al formulario listPelicula.html**/
    }


    @GetMapping("/listadoPeliculas/{id}")
    public String buscarPeliculasPorActor(Model model, @RequestParam(name="page", defaultValue="0") int page, @PathVariable("id") Integer id) {
        /** Este método utiliza buscarActorPorId() declarado en la clase IActoresService
         * para buscar un actor y extrer las peliculas que tiene almacenados en su array de peliculas.
         * para mostrarlos en el formulario de listPelicula.html*/
        Pageable pageable = PageRequest.of(page, 5);
        Actor actor = actoresService.buscarActorPorId(id);
        List<Pelicula> listadoA = actor.getPeliculas();
        Page<Pelicula> listado = new PageImpl<>(listadoA);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/listado", listado);
        model.addAttribute("titulo", "Listado de peliculas por actor");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula"; /** llama al  formulario listActor.html*/
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Actor actor = new Actor();
        model.addAttribute("titulo", "Nuevo actor");
        model.addAttribute("actor", actor);
        return "peliculas/formActor"; /**llama a formActor.html */
    }

    @PostMapping("/guardar/")
    public String guardarActor(Model model, Actor actor, RedirectAttributes attributes) {
        actoresService.guardarActor(actor);
        model.addAttribute("titulo", "Nuevo actor");
        attributes.addFlashAttribute("msg", "Los datos del  actor fueron guardados!");
        return "redirect:/cactores/listado"; /** Visualiza el listado de actores*/
    }
    @GetMapping("/editar/{id}")
    public String editarActor(Model model, @PathVariable("id") Integer id) {
        Actor actor = actoresService.buscarActorPorId(id);
        model.addAttribute("titulo", "Editar actor");
        model.addAttribute("actor", actor);
        return "peliculas/formActor"; /** Llama al formulario formActor.html**/
    }



    @GetMapping("/borrar/{id}")
    public String eliminarActor(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
       Actor actor = actoresService.buscarActorPorId(id);
       if (actor != null) {
         actoresService.eliminarActor(id);
         attributes.addFlashAttribute("msg", "Los datos del actor fueron borrados!");
         } else {
             attributes.addFlashAttribute("msg", "Actor no encontrado!");
        }
        return "redirect:/cactores/listado"; /** llama al formulario listActor.hmtl**/
    }
}
