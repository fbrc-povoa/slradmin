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

import com.fbrc.slradmin.exceptions.DMSAException;

@Service
public class CollectionService {
	
	private ConnectionService con;
	
	@Autowired
	public CollectionService(ConnectionService con) {
		this.con=con;
	}
	
	public List<String> collectionsName() {
		findAllCollectionName();
		hasDMSA();
		return getCurrentCollectionsName();
	}

	public List<String> getCurrentCollectionsName() {
		return Arrays.asList(System.getProperty("collectionsName").split(";"));
	}
	
	public void findAllCollectionName() {

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
	
	public void hasDMSA() {
		List<String> collectionsNameList = Arrays.asList(System.getProperty("collectionsName").split(";"));
		String DMSACollectionName = collectionsNameList.stream().filter(entry->entry.equalsIgnoreCase("DMSA")).findFirst().orElse(null);
		if(DMSACollectionName == null)
			throw new DMSAException("Collection DMSA not found!");
	}
	
	public List<String> getCurrentCollectionFields(){
		if(System.getProperty("collectionNameAdmin").equalsIgnoreCase("dmsa")) {
			return Arrays.asList("collection_name_s","attr_fields");
		}else {
			//TODO
			//Connect to DMSA collection
		}
		return null;
	}
}

