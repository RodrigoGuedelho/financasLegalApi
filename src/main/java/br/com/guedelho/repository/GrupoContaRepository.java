package br.com.guedelho.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.models.GrupoConta;

public interface GrupoContaRepository extends JpaRepository<GrupoConta, Long> {
	@Query("select g from GrupoConta g where g.descricao like :descricao "
		+ "and g.status = 'ATIVO'")
	public List<GrupoConta> findByDescricaoAtivos(String descricao);
	
	@Query("select g from GrupoConta g where g.descricao like :descricao and (g.id = :id or :id = 0) "
			+ "and (g.status = :status or :status is null )")
	public List<GrupoConta> find(@Param("descricao") String descricao, 
			@Param("id") Long id, @Param("status") StatusGenerico status);
}
