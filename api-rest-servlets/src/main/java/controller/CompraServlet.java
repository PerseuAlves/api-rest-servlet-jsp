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

import model.Compra;
import repository.CompraDao;
import service.ICompraDao;

@WebServlet(name = "compra", urlPatterns = {"/compra", "/compra/*", "/compraByCliente/*"})
public class CompraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Gson gson = new Gson();
    
    public CompraServlet() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI().substring(25);
		
		if (uri.isEmpty()) { // url "/compra"
			try {
				ICompraDao iCompraDao = new CompraDao();

				String json = gson.toJson(iCompraDao.findAll());
				
				returnResponse(response, 200, json);
			} catch (SQLException | ClassNotFoundException | NullPointerException | StringIndexOutOfBoundsException e) {
				returnResponse(response, 500, null);
			}
		} else if (uri.contains("Cliente")) { // url "/compraByCliente/*"
			try {
				String id = uri.substring(10); // get id from url
				
				ICompraDao iCompraDao = new CompraDao();
				
				String json = gson.toJson(iCompraDao.FindComprasByClienteId(Integer.parseInt(id)));
				
				returnResponse(response, 200, json);
			} catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException | StringIndexOutOfBoundsException e) {
				returnResponse(response, 500, null);
			}
		} else { // url "/compra/*"
			try {
				String id = uri.substring(1); // get id from url
				
				ICompraDao iCompraDao = new CompraDao();
				
				Compra compra = iCompraDao.findById(Integer.parseInt(id));
				
				if (compra.getId() == null) {			
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("error", "compra not found");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 404, json);
				} else {
					String json = gson.toJson(compra);
					
					returnResponse(response, 200, json);
				}
			} catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException | StringIndexOutOfBoundsException e) {
				returnResponse(response, 500, null);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI().substring(25);

		if (uri.isEmpty()) { // url "/compra"
			try {
				ICompraDao iCompraDao = new CompraDao();

				JSONObject jObj = new JSONObject(getBodyToJson(request));
				Map<String, Object> attributes = getMapOfJson(jObj);
				
				Compra compra = new Compra();
				
				compra.setId(Integer.parseInt(attributes.get("id").toString()));
				compra.setDtCompra(attributes.get("dtCompra").toString());
				compra.setQtd(Integer.parseInt(attributes.get("qtd").toString()));
				compra.setIdCliente(Integer.parseInt(attributes.get("idCliente").toString()));
				compra.setIdProduto(Integer.parseInt(attributes.get("idProduto").toString()));
				
				if (compra.getId() == null || 
					compra.getDtCompra().isBlank() || 
					compra.getQtd() == null ||
					compra.getIdCliente() == null ||
					compra.getIdProduto() == null) {
					
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("error", "invalid values");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 422, json);
				} else {
					if (iCompraDao.findById(compra.getId()).getId() != null) {
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("error", "compra already exists");
						
						JSONObject json = new JSONObject(hashMap);
						
						returnResponse(response, 422, json);
					} else {
						iCompraDao.save(compra);
						
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("message", "compra created");
						
						JSONObject json = new JSONObject(hashMap);
						
						returnResponse(response, 201, json);
					}
				}
			} catch (SQLException | ClassNotFoundException | IOException | NullPointerException | StringIndexOutOfBoundsException e) {
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
		String uri = request.getRequestURI().substring(25);
		
		if (uri.isEmpty()) { // url "/compra"
			try {
				ICompraDao iCompraDao = new CompraDao();

				JSONObject jObj = new JSONObject(getBodyToJson(request));
				Map<String, Object> attributes = getMapOfJson(jObj);
				
				Compra compra = new Compra();
				
				compra.setId(Integer.parseInt(attributes.get("id").toString()));
				compra.setDtCompra(attributes.get("dtCompra").toString());
				compra.setQtd(Integer.parseInt(attributes.get("qtd").toString()));
				compra.setIdCliente(Integer.parseInt(attributes.get("idCliente").toString()));
				compra.setIdProduto(Integer.parseInt(attributes.get("idProduto").toString()));
				
				if (compra.getId() == null || 
					compra.getDtCompra().isBlank() || 
					compra.getQtd() == null ||
					compra.getIdCliente() == null ||
					compra.getIdProduto() == null) {
					
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("error", "invalid values");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 422, json);
				} else {
					if (iCompraDao.findById(compra.getId()).getId() == null) {
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("error", "compra not found");
						
						JSONObject json = new JSONObject(hashMap);
						
						returnResponse(response, 404, json);
					} else {
						iCompraDao.save(compra);
						
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("message", "compra updated");
						
						JSONObject json = new JSONObject(hashMap);
						
						returnResponse(response, 200, json);
					}
				}
			} catch (SQLException | ClassNotFoundException | IOException | NullPointerException | StringIndexOutOfBoundsException e) {
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
		String uri = request.getRequestURI().substring(25);
		
		if (uri.isEmpty()) { // url "/compra"
			try {
				Map<String, String> hashMap = new HashMap<>();
				hashMap.put("error", "invalid request, url does not have a parameter for DELETE request");
				
				JSONObject json = new JSONObject(hashMap);
				
				returnResponse(response, 404, json);
			} catch (NullPointerException e) {
				returnResponse(response, 500, null);
			}
		} else { // url "/compra/*"
			try {
				String id = uri.substring(1); // get id from url
				
				ICompraDao iCompraDao = new CompraDao();
				
				Compra compra = iCompraDao.findById(Integer.parseInt(id));
				
				if (compra.getId() == null) {
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("error", "compra not found");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 404, json);
				} else {
					iCompraDao.delete(Integer.parseInt(id));
					
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put("message", "compra deleted");
					
					JSONObject json = new JSONObject(hashMap);
					
					returnResponse(response, 200, json);
				}
			} catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException | StringIndexOutOfBoundsException e) {
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
