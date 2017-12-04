package com.nettooe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nettooe.entity.TimeDoCoracao;

public interface TimeDoCoracaoRepository extends JpaRepository<TimeDoCoracao, Long> {

	TimeDoCoracao findByNome(String string);

}
