package br.com.guedelho.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.enums.TipoConta;
import br.com.guedelho.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{
	@Query("select c from Conta c where c.descricao like :descricao and (c.id = :id or :id = 0) "
			+ "and (c.status = :status or :status is null ) "
			+ "and (c.tipoConta = :tipoConta or :tipoConta is null)")
	public List<Conta> find(@Param("descricao") String descricao, 
			@Param("id") Long id, @Param("status") StatusGenerico status, 
			 @Param("tipoConta") TipoConta tipoConta);
	
}
