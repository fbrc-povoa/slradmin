package com.fbrc.slradmin.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {
	
	private ConnectionService con;
	
	@Autowired
	public CollectionService(ConnectionService con) {
		this.con=con;
	}
	
	public List<String> collectionsName() {
		setCurrentCollectionsName();
		return getCurrentCollectionsName();
	}

	public List<String> getCurrentCollectionsName() {
		return Arrays.asList(System.getProperty("collectionsName").split(";"));
	}
	
	public void setCurrentCollectionsName() {

		URI uri;
		StringJoiner sj = new StringJoiner(";");
		try {
			uri = new URIBuilder() //
					.setScheme("http") //
					.setHost(System.getProperty("solrAddress")) //
					.setPath("/solr/admin/collections") //
					.setParameter("action", "LIST") //
					.setParameter("wt", "json") //
					.build();

			JSONObject json = con.get(uri);
			for (int i = 0; i < json.getJSONArray("collections").length(); i++) {
				sj.add(json.getJSONArray("collections").getString(i));
			}
		} catch (URISyntaxException | JSONException e) {
			e.printStackTrace();
		}
		System.setProperty("collectionsName", sj.toString());
	}
	
	public String getCurrentAddress() {
		return System.getProperty("solrAddress");
	}
	
	public void setCurrentCollectionNameAdmin(String collectionNameAdmin) {
		System.setProperty("collectionNameAdmin", collectionNameAdmin);
	}
	
	public String getCurrentCollectionNameAdmin() {
		return System.getProperty("collectionNameAdmin");
	}
	
	public void create(String name) {
		
		URI uri;
		try {
			uri = new URIBuilder() //
					.setScheme("http") //
					.setHost(System.getProperty("solrAddress")) //
					.setPath("/solr/admin/collections") //
					.setParameter("action", "CREATE") //
					.setParameter("name", name) //
					.setParameter("numShards", "1") //
//					.setParameter("replicationFactor", "1") //
					.setParameter("collection.configName", "_default") //
					.build();
			JSONObject json = con.get(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
