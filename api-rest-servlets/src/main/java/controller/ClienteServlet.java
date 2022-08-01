package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import model.Cliente;
import repository.ClienteDao;
import service.IClienteDao;

@WebServlet(name = "cliente", urlPatterns = {"/cliente", "/cliente/*"})
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Gson gson = new Gson();
    
    public ClienteServlet() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI().substring(26);
		
		if (uri.isEmpty()) { // url "/cliente"
			try {
				IClienteDao iClienteDao = new ClienteDao();

				String json = gson.toJson(iClienteDao.findAll());
				
				returnResponse(response, 200, json);
			} catch (SQLException | ClassNotFoundException | NullPointerException e) {
				returnResponse(response, 500, null);
			}
		} else { // url "/cliente/*"
			try {
				String id = uri.substring(1); // get id from url
				
				IClienteDao iClienteDao = new ClienteDao();
				
				Cliente cliente = iClienteDao.findById(Integer.parseInt(id));
				
				if (cliente.getId() == null) {			
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("error", "cliente not found");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 404, json);
				} else {
					String json = gson.toJson(cliente);
					
					returnResponse(response, 200, json);
				}
			} catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e) {
				returnResponse(response, 500, null);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI().substring(26);

		if (uri.isEmpty()) { // url "/cliente"
			try {
				IClienteDao iClienteDao = new ClienteDao();

				JSONObject jObj = new JSONObject(getBodyToJson(request));
				Map<String, Object> attributes = getMapOfJson(jObj);
				
				Cliente cliente = new Cliente();
				
				cliente.setId(Integer.parseInt(attributes.get("id").toString()));
				cliente.setNome(attributes.get("nome").toString());
				cliente.setTelefone(attributes.get("telefone").toString());
				
				if (cliente.getId() == null || 
					cliente.getNome().isBlank() || 
					cliente.getTelefone().isBlank()) {
					
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("error", "invalid values");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 422, json);
				} else {
					if (iClienteDao.findById(cliente.getId()).getId() != null) {
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("error", "cliente already exists");
						
						JSONObject json = new JSONObject(hashMap);
						
						returnResponse(response, 422, json);
					} else {
						iClienteDao.save(cliente);
						
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("message", "cliente created");
						
						JSONObject json = new JSONObject(hashMap);
						
						returnResponse(response, 201, json);
					}
				}
			} catch (SQLException | ClassNotFoundException | IOException | NullPointerException e) {
				returnResponse(response, 500, null);
			}
		} else {
			Map<String, String> hashMap = new HashMap<>();
			hashMap.put("error", "invalid request, url has a parameter in a POST request");
			
			JSONObject json = new JSONObject(hashMap);
			
			returnResponse(response, 422, json);
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI().substring(26);
		
		if (uri.isEmpty()) { // url "/cliente"
			try {
				IClienteDao iClienteDao = new ClienteDao();

				JSONObject jObj = new JSONObject(getBodyToJson(request));
				Map<String, Object> attributes = getMapOfJson(jObj);
				
				Cliente cliente = new Cliente();
				
				cliente.setId(Integer.parseInt(attributes.get("id").toString()));
				cliente.setNome(attributes.get("nome").toString());
				cliente.setTelefone(attributes.get("telefone").toString());
				
				if (cliente.getId() == null || 
					cliente.getNome().isBlank() || 
					cliente.getTelefone().isBlank()) {
					
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("error", "invalid values");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 422, json);
				} else {
					if (iClienteDao.findById(cliente.getId()).getId() == null) {
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("error", "cliente not found");
						
						JSONObject json = new JSONObject(hashMap);
						
						returnResponse(response, 404, json);
					} else {
						iClienteDao.save(cliente);
						
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("message", "cliente updated");
						
						JSONObject json = new JSONObject(hashMap);
						
						returnResponse(response, 200, json);
					}
				}
			} catch (SQLException | ClassNotFoundException | IOException | NullPointerException e) {
				returnResponse(response, 500, null);
			}
		} else {
			Map<String, String> hashMap = new HashMap<>();
			hashMap.put("error", "invalid request, url has a parameter in a PUT request");
			
			JSONObject json = new JSONObject(hashMap);
			
			returnResponse(response, 422, json);
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI().substring(26);
		
		if (uri.isEmpty()) { // url "/cliente"
			try {
				Map<String, String> hashMap = new HashMap<>();
				hashMap.put("error", "invalid request, url does not have a parameter for DELETE request");
				
				JSONObject json = new JSONObject(hashMap);
				
				returnResponse(response, 404, json);
			} catch (NullPointerException e) {
				returnResponse(response, 500, null);
			}
		} else { // url "/cliente/*"
			try {
				String id = uri.substring(1); // get id from url
				
				IClienteDao iClienteDao = new ClienteDao();
				
				Cliente cliente = iClienteDao.findById(Integer.parseInt(id));
				
				if (cliente.getId() == null) {
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("error", "cliente not found");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 404, json);
				} else {
					iClienteDao.delete(Integer.parseInt(id));
					
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("message", "cliente deleted");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 200, json);
				}
			} catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e) {
				returnResponse(response, 500, null);
			}
		}
	}
	
	/*
	 * return the request body to JSON in String format
	 * */
	private String getBodyToJson(HttpServletRequest request) throws IOException {
	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

        InputStream inputStream = request.getInputStream();
        Charset charset = Charset.forName("UTF-8");
        
        if (inputStream != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } else {
            stringBuilder.append("");
        }
        
        if (bufferedReader != null) {
            bufferedReader.close();
        }

	    body = stringBuilder.toString();
	    return body;
	}
	
	/*
	 * return the Map of the JSONObject
	 * */
	private Map<String, Object> getMapOfJson(JSONObject jObj) {
		Iterator<String> iterator = jObj.keys();
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		
		while (iterator.hasNext()) {
			String key = iterator.next(); // get key
			Object obj = jObj.get(key); // get value
			attributes.put(key, obj);
		}
		
		return attributes;
	}
	
	/*
	 * return custom response
	 * */
	private void returnResponse(HttpServletResponse response, Integer httpStatus, Object json) {
		try {
			if (!httpStatus.equals(500)) {
				response.setStatus(httpStatus);
				response.setHeader("Content-Type", "application/json");
				response.getOutputStream().println(json.toString());
			} else {
				Map<String, String> hashMap = new HashMap<>();
				hashMap.put("error", "internal server error");
				
				response.setStatus(500);
				response.setHeader("Content-Type", "application/json");
				response.getOutputStream().println(new JSONObject(hashMap).toString());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
