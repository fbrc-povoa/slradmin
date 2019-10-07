package com.fbrc.slradmin.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.fbrc.slradmin.dtos.ConnectionDto;

@Service
public class ConnectionService {

	public boolean start(final ConnectionDto dto) {
		setProperties(dto.getAddress());
		return test();
	}
	
	public void setProperties(final String address) {
		System.setProperty("solrAddress", address);
	}
	
	public boolean test() {
		
		URI uri = null;
		try {
			uri = new URIBuilder()
					.setScheme("http")
					.setHost(System.getProperty("solrAddress"))
					.setPath("/solr/admin/collections")
					.setParameter("action", "LIST")
					.setParameter("wt", "json")
					.build();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HttpGet httpget = new HttpGet(uri);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpclient.execute(httpget);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			httpclient.execute(httpget);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
}
