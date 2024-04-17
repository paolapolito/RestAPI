package ps2.restapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DisciplinaController {

    private List<Disciplina> disciplinas;

    public DisciplinaController() {
        this.disciplinas = new ArrayList<>();
        disciplinas.add(new Disciplina(10, "Programação de Sistemas 2", "PGS2", "Sistemas de Informação", 3));
        disciplinas.add(new Disciplina(20, "Desenvolvimento de Sistemas ", "DS", "Análise de Sistemas", 2));
        disciplinas.add(new Disciplina(30, "Estrutura de dados", "ED", "Ciência da Computação", 3));
    }

    @GetMapping("/api/disciplinas")
    Iterable<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    @GetMapping("/api/disciplinas/{id}")
    Optional<Disciplina> getDisciplina(@PathVariable long id) {
        return this.disciplinas.stream().filter(d -> d.getId() == id).findFirst();
    }

    @PostMapping("/api/disciplinas")
    Disciplina createDisciplina(@RequestBody Disciplina disciplina) {
        long maxId = this.disciplinas.stream().mapToLong(Disciplina::getId).max().orElse(0);
        disciplina.setId(maxId + 1);
        this.disciplinas.add(disciplina);
        return disciplina;
    }

    @PutMapping("/api/disciplinas/{id}")
    Optional<Disciplina> updateDisciplina(@RequestBody Disciplina disciplinaRequest, @PathVariable long id) {
        Optional<Disciplina> opt = this.getDisciplina(id);
        if (opt.isPresent()) {
            Disciplina disciplina = opt.get();
            disciplina.setNome(disciplinaRequest.getNome());
            disciplina.setSigla(disciplinaRequest.getSigla());
            disciplina.setCurso(disciplinaRequest.getCurso());
            disciplina.setSemestre(disciplinaRequest.getSemestre());
        }
        return opt;
    }

    @DeleteMapping("/api/disciplinas/{id}")
    void deleteDisciplina(@PathVariable long id) {
        this.disciplinas.removeIf(d -> d.getId() == id);
    }
}
