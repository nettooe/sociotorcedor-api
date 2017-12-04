package com.nettooe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nettooe.entity.SocioTorcedor;

public interface SocioTorcedorRepository extends JpaRepository<SocioTorcedor, Long>, SocioTorcedorRepositoryQuery {



}
