package es.uah.clientePeliculasSeguro.controller;

import es.uah.clientePeliculasSeguro.model.Pelicula;
import es.uah.clientePeliculasSeguro.model.Actor;

import es.uah.clientePeliculasSeguro.paginator.PageRender;
import es.uah.clientePeliculasSeguro.service.IPeliculasService;
import es.uah.clientePeliculasSeguro.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/cpeliculas") /**Establece la direccion base del lado del cliente*/
public class PeliculasController {

    @Autowired
    IPeliculasService peliculasService;

        @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        return "home"; /**llama a home.html*/
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("titulo", "Detalle de la película: " + pelicula.getTitulo());
        return "peliculas/verPelicula"; /** llama a verPelicula.html*/
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("titulo", "Nueva película");
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula"; /**llama a formPelicula.html */
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "peliculas/searchPelicula";/** llama a searchPelicula.html */
    }

    @GetMapping("/listado")
    public String listadoPeliculas(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarTodos(pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/cpeliculas/listado", listado);
        model.addAttribute("titulo", "Listado de todas las peliculas");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula"; /** llama al formulario listPelicula.html**/
    }



    @GetMapping("/idpelicula/{id}")
    public String buscarPeliculaPorId(Model model, @PathVariable("id") Integer id) {
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula"; /** llama al formulario formPelicula.html**/
    }

    @GetMapping("/titulo")
    public String buscarPeliculasPorTitulo(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("titulo") String titulo) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarPeliculasPorTitulo(titulo, pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/listado", listado);
        model.addAttribute("titulo", "Listado de peliculas por titulo");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula"; /** llama al formulario listPelicula.html**/
    }

    @GetMapping("/genero")
    public String buscarPeliculasPorGenero(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("genero") String genero) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarPeliculasPorGenero(genero, pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/listado", listado);
        model.addAttribute("titulo", "Listado de peliculas por genero");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @GetMapping("/director")
    public String buscarPeliculasPorDirector(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("director") String director) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarPeliculasPorDirector(director, pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/listado", listado);
        model.addAttribute("titulo", "Listado de peliculas por director");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula"; /** llama al formulario listPelicula.html**/
    }

    @GetMapping("/listadoActores/{id}")
    public String buscarActoresPorPelicula(Model model, @RequestParam(name="page", defaultValue="0") int page, @PathVariable("id") Integer id) {
        /** Este método utiliza buscarPeliculaPorId() declarado en la clase IPeliculasService
         * para buscar una pelicula y extrer los actores que tiene almacenados en su array de actores.
         * para mostrarlos en el formulario de listActor.html*/
        Pageable pageable = PageRequest.of(page, 5);
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        List<Actor> listadoA = pelicula.getActores();
        Page<Actor> listado = new PageImpl<>(listadoA);
        PageRender<Actor> pageRender = new PageRender<Actor>("/listado", listado);
        model.addAttribute("titulo", "Listado de actores por pelicula");
        model.addAttribute("listadoActores", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listActor"; /** llama al  formulario listActor.html*/
    }




    @PostMapping("/guardar/")
    public String guardarPelicula(Model model, Pelicula pelicula,
                               @RequestParam("file") MultipartFile foto, RedirectAttributes attributes) {
        if(pelicula != null) {
            System.out.println(pelicula.getTitulo());
        }

        if (!foto.isEmpty()) {

            if (pelicula.getIdPelicula() != null && pelicula.getIdPelicula() > 0 && pelicula.getImagen() != null
                    && pelicula.getImagen().length() > 0) {

                uploadFileService.delete(pelicula.getImagen());
            }

            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            attributes.addFlashAttribute("msg", "Has subido correctamente '" + uniqueFilename + "'");

            pelicula.setImagen(uniqueFilename);
        }

        peliculasService.guardarPelicula(pelicula);
        model.addAttribute("titulo", "Nueva pelicula");
        attributes.addFlashAttribute("msg", "Los datos de la pelicula fueron guardados!");
        return "redirect:/cpeliculas/listado"; /**Visualiza el listado de peliculas **/
    }

    @GetMapping("/editar/{id}")
    public String editarPelicula(Model model, @PathVariable("id") Integer id) {
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        model.addAttribute("titulo", "Editar pelicula");
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula"; /** llama al  formulario formPelicula.html*/
    }

    @GetMapping("/borrar/{id}")
    public String eliminarPelicula(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        if (uploadFileService.delete(pelicula.getImagen())) {
            attributes.addFlashAttribute("msg", "Imagen " + pelicula.getImagen() + " eliminada con exito!");
        }

        peliculasService.eliminarPelicula(id);
        attributes.addFlashAttribute("msg", "Los datos de la película fueron borrados!");

        return "redirect:/cpeliculas/listado"; /**Visualiza el listado de peliculas **/
    }

}

