package br.com.pereira.servletsclient.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pereira.servletsclient.model.Cliente;

@Controller
public class WebController {
   
	@PostMapping(name = "webController", value = "/webController")
	public ModelAndView redirect(@RequestParam Map<String, String> allRequestParams, Model model) {
		String acao = allRequestParams.get("botao");
		
		try {
			if (acao.equals("Find All Clientes")) {
				String URL = "http://localhost:8080/api-rest-servlets/cliente";
				
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder()
				    .uri(URI.create(URL))
				    .header("Content-Type", "application/json")
				    .GET()
				    .build();
				
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				return jsonStringResponseToModelAndView(response.body(), model);
			} else if (acao.equals("Find Cliente By ID")) {
				
				model.addAttribute("inputFindById", true);
				return new ModelAndView("index.jsp");
			} else if (acao.equals("Get Cliente By ID")) {
				String URL = "http://localhost:8080/api-rest-servlets/cliente/" + allRequestParams.get("idCliente");
				
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder()
				    .uri(URI.create(URL))
				    .header("Content-Type", "application/json")
				    .GET()
				    .build();
				
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				return jsonStringResponseToModelAndView(response.body(), model);
			} else if (acao.equals("Post Cliente")) {
				
				model.addAttribute("inputPostCliente", true);
				return new ModelAndView("index.jsp");
			} else if (acao.equals("Save Cliente by POST")) {
				String URL = "http://localhost:8080/api-rest-servlets/cliente";
				
				Cliente cliente = new Cliente();
				
				cliente.setId(Integer.parseInt(allRequestParams.get("idCliente")));
				cliente.setNome(allRequestParams.get("nomeCliente"));
				cliente.setTelefone(allRequestParams.get("telefoneCliente"));
				
				ObjectMapper objectMapper = new ObjectMapper();
				String requestBody = objectMapper
					      .writerWithDefaultPrettyPrinter()
					      .writeValueAsString(cliente);
				
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder()
				    .uri(URI.create(URL))
				    .header("Content-Type", "application/json")
				    .POST(BodyPublishers.ofString(requestBody))
				    .build();
				
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				model.addAttribute("saida", response.body());
				return new ModelAndView("index.jsp");
			} else if (acao.equals("Put Cliente")) {

				model.addAttribute("inputPutCliente", true);
				return new ModelAndView("index.jsp");
			} else if (acao.equals("Save Cliente by PUT")) {
				String URL = "http://localhost:8080/api-rest-servlets/cliente";
				
				Cliente cliente = new Cliente();
				
				cliente.setId(Integer.parseInt(allRequestParams.get("idCliente")));
				cliente.setNome(allRequestParams.get("nomeCliente"));
				cliente.setTelefone(allRequestParams.get("telefoneCliente"));
				
				ObjectMapper objectMapper = new ObjectMapper();
				String requestBody = objectMapper
					      .writerWithDefaultPrettyPrinter()
					      .writeValueAsString(cliente);
				
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder()
				    .uri(URI.create(URL))
				    .header("Content-Type", "application/json")
				    .PUT(BodyPublishers.ofString(requestBody))
				    .build();
				
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				model.addAttribute("saida", response.body());
				return new ModelAndView("index.jsp");
			} else if (acao.equals("Delete Cliente")) {
				
				model.addAttribute("inputDeleteCliente", true);
				return new ModelAndView("index.jsp");
			} else if (acao.equals("Delete Cliente By ID")) {
				String URL = "http://localhost:8080/api-rest-servlets/cliente/" + allRequestParams.get("idCliente");
				
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder()
				    .uri(URI.create(URL))
				    .header("Content-Type", "application/json")
				    .DELETE()
				    .build();
				
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				model.addAttribute("saida", response.body());
				return new ModelAndView("index.jsp");
			} else {
				model.addAttribute("erro", "input inválido, tente novamente");
				return new ModelAndView("index.jsp");
			}
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return new ModelAndView("index.jsp");
		}
	}
	
	/*
	 * JSON String to ModelAndView
	 * */
	private ModelAndView jsonStringResponseToModelAndView(String saida, Model model) {
		if (saida.contains("[")) {
			saida = saida.replace('[', ' ');
			saida = saida.replace(']', ' ');
			
			if (saida.contains("{")) {
				saida = saida.replace('{', ' ');
				saida = saida.replace('}', ' ');

				String[] listaClientes = saida.split(" , ");				
				String[] listaClientesFormatadaAspas = new String[listaClientes.length];
				
				for (int i=0; i<listaClientes.length; i++) {
					listaClientesFormatadaAspas[i] = listaClientes[i].replace('"', ' ');
				}
				
				String[] listaClientesFinal = new String[listaClientesFormatadaAspas.length];
				
				for (int i=0; i<listaClientesFormatadaAspas.length; i++) {
					listaClientesFinal[i] = listaClientesFormatadaAspas[i].replace(',', '/');
				}
				
				model.addAttribute("saidaFindAll", listaClientesFinal);
				return new ModelAndView("index.jsp");
			} else {
				model.addAttribute("erro", "clientes não encontrados");
				return new ModelAndView("index.jsp");
			}
		} else {
			if (saida.contains("{")) {
				saida = saida.replace('{', ' ');
				saida = saida.replace('}', ' ');
				
				saida = saida.replace('"', ' ');
				
				model.addAttribute("saidaFindAll", saida);
				return new ModelAndView("index.jsp");
			} else {
				model.addAttribute("erro", "clientes não encontrados");
				return new ModelAndView("index.jsp");
			}
		}
	}
}
