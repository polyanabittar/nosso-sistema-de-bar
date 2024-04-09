package br.com.zup.edu.nossosistemadebares.bar;

import br.com.zup.edu.nossosistemadebares.bar.request.MesaRequest;
import br.com.zup.edu.nossosistemadebares.bar.request.ReservaRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/mesas")
public class MesaController {
    private final MesaRepository repository;

    public MesaController(MesaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid MesaRequest request, UriComponentsBuilder uriComponentsBuilder){

        Mesa mesa = request.paraMesa();

        repository.save(mesa);

        URI location = uriComponentsBuilder.path("/mesas/{id}")
                .buildAndExpand(mesa.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @PatchMapping
    public ResponseEntity<?> reservar(@RequestBody @Valid ReservaRequest request){

        Optional<Mesa> existeMesa = repository.findById(request.getIdMesa());

        if(existeMesa.isEmpty() || !existeMesa.get().estaLivre()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Mesa mesa = existeMesa.get();

        mesa.reservar(request);
        repository.save(mesa);

        return ResponseEntity.ok().body("Mesa " + mesa.getId() + " reservada.");
    }
}
