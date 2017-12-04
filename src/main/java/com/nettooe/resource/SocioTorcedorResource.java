package com.nettooe.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nettooe.RecursoCriadoEvent;
import com.nettooe.entity.SocioTorcedor;
import com.nettooe.repository.SocioTorcedorRepository;
import com.nettooe.repository.exception.ResourcesExceptionHandler.Erro;
import com.nettooe.vo.CadastroSocioTorcedor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sociotorcedores")
@Api(description = "Serviço de Gerenciamento de Sócio Torcedores")
public class SocioTorcedorResource {

	@Autowired
	private SocioTorcedorRepository socioTorcedorRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@ApiOperation(value = "Listar todos os Sócio Torcedores", notes = "Lista todos os Sócio Torcedores cadastrados")
	@ApiResponses(value = {
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public List<SocioTorcedor> listar() {
		return this.socioTorcedorRepository.findAll();
	}

	@PostMapping
	@ApiOperation(value = "Grava uma socioTorcedor", notes = "Grava uma nova socioTorcedor com os dados informados.", response = SocioTorcedor.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successo ao criar a SocioTorcedor", response = SocioTorcedor.class),
			@ApiResponse(code = 400, message = "Excessão de negócio.", response = Erro.class),
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public ResponseEntity<SocioTorcedor> criar(@Valid @RequestBody CadastroSocioTorcedor cadastroSocioTorcedor,
			HttpServletResponse response) {
		SocioTorcedor socioTorcedorSalvo = this.socioTorcedorRepository.gravarEAssociarCampanhas(cadastroSocioTorcedor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, socioTorcedorSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(socioTorcedorSalvo);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Consultar Sócio Torcedor", notes = "Retorna os dados de um Sócio Torcedor através do ID informado.", response = SocioTorcedor.class)
	@ApiResponses(value = {
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public ResponseEntity<SocioTorcedor> buscarPeloCodigo(@PathVariable Long id) {
		return Optional.ofNullable(this.socioTorcedorRepository.findOne(id))
				.map(socioTorcedor -> ResponseEntity.ok().body(socioTorcedor)).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Excluir Sócio Torcedor", notes = "Exclui um Sócio Torcedor através do ID informado.")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Successo. Sem conteúdo para retornar."),
			@ApiResponse(code = 400, message = "Excessão de negócio.", response = Erro.class),
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public void remover(@PathVariable Long id) {
		this.socioTorcedorRepository.delete(id);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar Sócio Torcedor", notes = "Atualiza os dados de um Sócio Torcedor através do ID informado.", response = SocioTorcedor.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successo.", response = SocioTorcedor.class),
			@ApiResponse(code = 400, message = "Excessão de negócio.", response = Erro.class),
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public ResponseEntity<SocioTorcedor> atualizar(@PathVariable Long id, @Valid @RequestBody CadastroSocioTorcedor cadastro) {
		try {
			return Optional.ofNullable(this.socioTorcedorRepository.findOne(id))
					.map(socioTorcedor -> this.atualizar(socioTorcedor, cadastro))
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private ResponseEntity<SocioTorcedor> atualizar(SocioTorcedor socioTorcedorBD, CadastroSocioTorcedor cadastro) {
		BeanUtils.copyProperties(cadastro, socioTorcedorBD, "id");
		this.socioTorcedorRepository.atualizarEAssociarCampanhas(socioTorcedorBD, cadastro);
		return ResponseEntity.ok().body(socioTorcedorBD);
	}

}
