package br.cefetmg.sicsec.Service;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Usuario.Matricula;
import br.cefetmg.sicsec.Model.Util.CPF;
import br.cefetmg.sicsec.Repository.MatriculaRepo;

@Service
public class MatriculaService {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private MatriculaRepo matriculaRepository;

    public Object matricular(String cpf, String nome, String email, String telefone, Long cursoId) {
        String cpfLimpo = cpf.replaceAll("\\D", "");
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }

        Long cpfLong = Long.parseLong(cpfLimpo);

        CPF cpfObject = new CPF();
        cpfObject.setCpf(cpfLong);
        cpfObject.setCpfFormatado(cpf);

        Matricula matricula = new Matricula();
        matricula.setCpf(cpfObject);
        matricula.setNome(nome);
        matricula.setEmail(email);
        matricula.setTelefone(telefone);

        Curso curso = cursoService.getCursoById(cursoId);
        if (curso == null) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        matricula.setCurso(curso);

        Long numeroMatricula = gerarNumeroMatricula(nome, cpf, email, telefone);
        matricula.setNumeroMatricula(numeroMatricula);

        matriculaRepository.save(matricula);
        return matricula;
    }

    private Long gerarNumeroMatricula(String nome, String cpf, String email, String telefone) {
        String ano = String.valueOf(Year.now().getValue());

        int hash = Math.abs((nome + cpf + email + telefone).hashCode()) % (100_000);

        String matriculaBase = String.format("%s%05d", ano, hash);

        Long matriculaLong = Long.parseLong(matriculaBase);
        while (matriculaRepository.existsByNumeroMatricula(matriculaLong)) {
            hash = (hash + 1) % 100000;
            matriculaLong = Long.parseLong(String.format("%s%05d", ano, hash));
        }

        return matriculaLong;
    }

    public Matricula buscarPorNumero(String numeroMatricula) {
        Long numeroMatriculaLong = Long.parseLong(numeroMatricula);
        return matriculaRepository.findByNumeroMatricula(numeroMatriculaLong);
    }

    public void atualizar(String cpf, String nome, String email, String telefone, String cursoId, String numeroMatriculaAnterior, String numeroMatriculaNovo) {
        Matricula matricula = buscarPorNumero(numeroMatriculaAnterior);
        if (matricula == null) {
            throw new IllegalArgumentException("Matrícula inexistente!");
        }

        Matricula novaMatricula = buscarPorNumero(numeroMatriculaNovo);
        if(novaMatricula != null && novaMatricula.getId() != matricula.getId()) {
            throw new IllegalArgumentException("Número de matrícula já existente!");
        }

        String cpfLimpo = cpf.replaceAll("\\D", "");
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }

        Long cpfLong = Long.parseLong(cpfLimpo);
        CPF cpfObject = new CPF();
        cpfObject.setCpf(cpfLong);
        cpfObject.setCpfFormatado(cpf);

        matricula.setCpf(cpfObject);
        matricula.setNome(nome);
        matricula.setEmail(email);
        matricula.setTelefone(telefone);

        Long cursoIdLong = Long.parseLong(cursoId);
        Curso curso = cursoService.getCursoById(cursoIdLong);
        if (curso == null) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        matricula.setCurso(curso);

        Long numeroMatriculaNovoLong = Long.parseLong(numeroMatriculaNovo);
        matricula.setNumeroMatricula(numeroMatriculaNovoLong);

        matriculaRepository.save(matricula);
    }
}
