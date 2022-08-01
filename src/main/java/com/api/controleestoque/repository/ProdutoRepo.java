package com.api.controleestoque.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.controleestoque.model.Produto;



@Repository
public interface ProdutoRepo extends CrudRepository<Produto, String>{
	Produto findByCodigo(long codigo);
		
}
