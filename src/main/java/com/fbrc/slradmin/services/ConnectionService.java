package com.fbrc.slradmin.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fbrc.slradmin.dtos.ConnectionDto;

@Service
public class ConnectionService {

	public boolean start(final ConnectionDto dto) throws JSONException {
		setProperties(dto.getAddress());
		return test();
	}

	public void setProperties(final String address) {
		System.setProperty("solrAddress", address);
	}

	public boolean test() {
		try {
			URI uri = new URIBuilder() //
					.setScheme("http") //
					.setHost(System.getProperty("solrAddress")) //
					.setPath("/solr/admin/collections") //
					.setParameter("action", "LIST") //
					.setParameter("wt", "json") //
					.build();
			JSONObject json = get(uri);
			if (json != null) {
				return true;
			} else {
				return false;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public JSONObject get(URI uri) {

		HttpGet httpget = new HttpGet(uri);
		try ( //
				CloseableHttpClient httpclient = HttpClients.createDefault(); //
				CloseableHttpResponse response = httpclient.execute(httpget) //
		) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return new JSONObject(result.toString());
		} catch (IOException | JSONException e1) {
			e1.printStackTrace();
			return null;
		}
	}

}
