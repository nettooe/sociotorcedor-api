package com.nettooe.repository;

import com.nettooe.entity.SocioTorcedor;
import com.nettooe.vo.CadastroSocioTorcedor;

public interface SocioTorcedorRepositoryQuery {

	SocioTorcedor gravarEAssociarCampanhas(CadastroSocioTorcedor cadastroSocioTorcedor);
	
	SocioTorcedor atualizarEAssociarCampanhas(SocioTorcedor socioTorcedor, CadastroSocioTorcedor cadastro);


}
