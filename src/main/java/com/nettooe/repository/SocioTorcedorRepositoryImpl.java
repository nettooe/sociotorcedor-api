package com.nettooe.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.nettooe.entity.Associacao;
import com.nettooe.entity.SocioTorcedor;
import com.nettooe.entity.TimeDoCoracao;
import com.nettooe.repository.exception.EmailInvalidoException;
import com.nettooe.vo.CadastroSocioTorcedor;

public class SocioTorcedorRepositoryImpl implements SocioTorcedorRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	SocioTorcedorRepository socioTorcedorRepository;

	@Autowired
	TimeDoCoracaoRepository timeDoCoracaoRepository;

	@Override
	public SocioTorcedor gravarEAssociarCampanhas(CadastroSocioTorcedor socioTorcedor) {

		boolean valid = new EmailValidator().isValid(socioTorcedor.getEmail(), null);
		if (!valid) {
			throw new EmailInvalidoException();
		}

		SocioTorcedor socioTorcedorGravado = this.gravarSocioTorcedor(socioTorcedor);
		this.associarCompanhas(socioTorcedorGravado);

		return socioTorcedorGravado;
	}

	@Transactional
	private SocioTorcedor gravarSocioTorcedor(CadastroSocioTorcedor cadastro) {

		TimeDoCoracao timeDoCoracao = this.timeDoCoracaoRepository.findByNome(cadastro.getMeuTimeDoCoracao());
		if (timeDoCoracao == null) {
			timeDoCoracao = this.timeDoCoracaoRepository.save(new TimeDoCoracao(cadastro.getMeuTimeDoCoracao()));
		}

		SocioTorcedor socioTorcedorNovo = new SocioTorcedor();
		BeanUtils.copyProperties(cadastro, socioTorcedorNovo, "meuTimeDoCoracao");

		socioTorcedorNovo.setTimeDoCoracao(timeDoCoracao);

		return this.socioTorcedorRepository.save(socioTorcedorNovo);

	}

	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void associarCompanhas(SocioTorcedor socioTorcedor) {

		try {
			RestTemplate restTemplate = new RestTemplate();
			ArrayList<Associacao> result = restTemplate.getForObject("http://localhost:8090/campanhas/time/{id}",
					ArrayList.class, socioTorcedor.getTimeDoCoracao().getId());

			List<Associacao> associacoes = socioTorcedor.getAssociacoes();

			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				LinkedHashMap campanha = (java.util.LinkedHashMap) iterator.next();

				Associacao associacao = new Associacao();
				Integer id = (Integer) campanha.get("id");
				associacao.setIdCampanha(id.longValue());
				associacao.setNomeDaCampanha((String) campanha.get("nomeDaCampanha"));
				associacao.setSocioTorcedor(socioTorcedor);
				associacoes.add(associacao);
			}

			if (result != null) {
				socioTorcedor.setAssociacoes(associacoes);
				this.socioTorcedorRepository.save(socioTorcedor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SocioTorcedor atualizarEAssociarCampanhas(SocioTorcedor socioTorcedor, CadastroSocioTorcedor cadastro) {

		TimeDoCoracao timeDoCoracao = this.timeDoCoracaoRepository.findByNome(cadastro.getMeuTimeDoCoracao());
		if (timeDoCoracao == null) {
			timeDoCoracao = this.timeDoCoracaoRepository.save(new TimeDoCoracao(cadastro.getMeuTimeDoCoracao()));
		}

		socioTorcedor.setTimeDoCoracao(timeDoCoracao);

		socioTorcedor.setAssociacoes(new ArrayList<Associacao>());

		SocioTorcedor save = this.socioTorcedorRepository.save(socioTorcedor);
		this.associarCompanhas(save);

		return save;
	}

}
