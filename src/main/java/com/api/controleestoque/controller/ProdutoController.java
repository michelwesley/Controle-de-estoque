package com.api.controleestoque.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.controleestoque.model.Produto;
import com.api.controleestoque.repository.ProdutoRepo;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoRepo pr;

	@RequestMapping(value = "/cadastrarProduto", method = RequestMethod.GET)
	public String form() {
		return "produto/formProduto";
	}

	@RequestMapping(value = "/cadastrarProduto", method = RequestMethod.POST)
	public String form(@Valid Produto produto, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarProduto";
		}
		
		pr.save(produto);
		attributes.addFlashAttribute("mensagem", "Produto cadastrado com sucesso!");
		return "redirect:/cadastrarProduto";
	}

	@RequestMapping("/produtos")
	public ModelAndView listaProdutos() {
		ModelAndView mv = new ModelAndView("listaProdutos");
		Iterable<Produto> produtos = pr.findAll();
		mv.addObject("produtos", produtos);
		return mv;

	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesProduto(@PathVariable("codigo") long codigo) {
		Produto produto = pr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("produto/detalhesProduto");
		mv.addObject("produto", produto);
		return mv;
	}
	
	@RequestMapping(value = "/editar/{codigo}", method = RequestMethod.GET)
	public ModelAndView editarProduto(@PathVariable(name="codigo") long codigo) {
		Produto produto = pr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("produto/editarProduto");
		mv.addObject("produto", produto);
		return mv;
	}
	

	@RequestMapping("/deletar")
	public String deletarProduto(long codigo) {
		Produto produto = pr.findByCodigo(codigo);
		pr.delete(produto);
		return "redirect:/produtos";
	}

}