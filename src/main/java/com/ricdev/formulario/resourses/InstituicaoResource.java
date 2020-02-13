package com.ricdev.formulario.resourses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ricdev.formulario.model.entities.Instituicao;

@RestController
@RequestMapping(value = "/intituicoes")
public class InstituicaoResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Instituicao> listar() {
		Instituicao instituicao1 = new Instituicao(1, "CCA-sao sebastiao");
		Instituicao instituicao2 = new Instituicao(2, "CCA- test");
		
		List<Instituicao>lista = new ArrayList<>();
		lista.add(instituicao1);
		lista.add(instituicao2);	
		return lista;
		
	}

}
