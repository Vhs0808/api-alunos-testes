package com.delegrego.api_alunos_testes.service;

import com.delegrego.api_alunos_testes.dto.AlunoRequest;
import com.delegrego.api_alunos_testes.dto.AlunoResponse;
import com.delegrego.api_alunos_testes.model.Aluno;
import com.delegrego.api_alunos_testes.repository.AlunoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;
    @Mock
    private AlunoRepository alunoRepository;

    @Test
    void deveSalvarERetornarAluno(){
        //Arrange
        AlunoRequest request = new AlunoRequest
                ("Vitor", "vitor@gmail.com", "vitor123",
                        LocalDate.of(2000, 1, 1), 7.8);
        //Stub -> parte
        Mockito.when(alunoRepository.existsByEmailIgnoreCase(request.getEmail()))
                .thenReturn(false);
        Aluno aluno = new Aluno(1L, request.getNome(), request.getEmail(), request.getSenha(),
                request.getDataNascimento(), request.getMedia());
        Mockito.when(alunoRepository.save(Mockito.any(Aluno.class)))
                .thenReturn(aluno);
        //Act
        AlunoResponse response = alunoService.cadastrarAluno(request);
        //Assert
        Assertions.assertThat(response.getId()).isEqualTo(aluno.getId());
        Assertions.assertThat(response.getNome()).isEqualTo(request.getNome());
        Assertions.assertThat(response.getEmail()).isEqualTo(request.getEmail());
        Assertions.assertThat(response.getDataNascimento()).isEqualTo(request.getDataNascimento());
        Assertions.assertThat(response.getMedia()).isEqualTo(request.getMedia());
    }

    @Test
    void deveRetornarListaDeAlunos(){
        //Arrange
        Aluno aluno1 = new Aluno(1L, "Vitor", "vitor@gmail.com", "senha",
                LocalDate.of(2000, 01, 01), 8.8);
        Aluno aluno2 = new Aluno(1L, "Pedro", "pedro@gmail.com", "senha",
                LocalDate.of(2000, 01, 01), 8.8);

        //Stub -> parte
        Mockito.when(alunoRepository.findAll()).thenReturn(List.of(aluno1, aluno2));

        //Act
        List<AlunoResponse> alunos = alunoService.listarAlunos();

        //Assert
        Assertions.assertThat(alunos).hasSize(2);
    }

    @Test
    void deveRetornarAlunoResponseQuandoIdValido(){
        //Arrange
        Aluno aluno = new Aluno(1L, "Vitor", "vitor@gmail.com", "senha",
                LocalDate.of(2000, 01, 01), 8.8);
        //Stub -> parte
        Mockito.when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        //Act
        AlunoResponse response = alunoRepository.findById(1L);
        //Assert
        Assertions.assertThat(response.getId()).isEqualTo(aluno.getId());
        Assertions.assertThat(response.getNome()).isEqualTo(aluno.getNome());
        Assertions.assertThat(response.getEmail()).isEqualTo(aluno.getEmail());
        Assertions.assertThat(response.getDataNascimento())
                .isEqualTo(aluno.getDataNascimento());
        Assertions.assertThat(response.getMedia()).isEqualTo(aluno.getMedia());
    }

}
