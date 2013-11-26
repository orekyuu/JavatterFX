package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.github.orekyuu.javatterfx.account.AccountManager;
import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class LoginController implements Initializable, HttpHandler {
	private HttpServer server;
	private static final byte[] RESPONSE = "<!DOCTYPE html><html lang=\"ja\"><head><meta charset=\"utf-8\"><title>認証完了</title></head><body>認証完了しました</body></html>"
			.getBytes();

	@FXML
	private BorderPane root;
	@FXML
	private BorderPane border;
	@FXML
	private TextField text;

	private RequestToken token;

	private Twitter twitter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		twitter=TwitterManager.getInstance().getTwitter();
	}
	
	
	public void browse(Application app){
		try {
			server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
			server.createContext("/", this);
			server.start();
			token=twitter.getOAuthRequestToken(String.format("http://localhost:%d/", server.getAddress().getPort()));
			app.getHostServices().showDocument(token.getAuthenticationURL());
		} catch (TwitterException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(HttpExchange ex) throws IOException {
		String queryString = ex.getRequestURI().getQuery();
		Map<String, String> query = parseQuery(queryString);

		try {
			AccessToken t = twitter.getOAuthAccessToken(query.get("oauth_verifier"));
			AccountManager.getInstance().setAccessToken(t);
			TwitterManager.getInstance().authentication(t);
			TwitterManager.getInstance().getTwitter().setOAuthAccessToken(t);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					root.getScene().getWindow().hide();
				}
			});
		} catch (TwitterException e) {
			e.printStackTrace();
		} finally {
			sendResponse(ex);
			server.stop(0);
		}
	}

	private static void sendResponse(HttpExchange ex) {
		OutputStream out = ex.getResponseBody();
		try {
			ex.sendResponseHeaders(200, RESPONSE.length);
			out.write(RESPONSE);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ex.close();
		}
	}

	private static Map<String, String> parseQuery(String queryString) {
		Map<String, String> query = new HashMap<String, String>();
		for (String param : queryString.split("&")) {
			String[] splitted = param.split("=");
			query.put(splitted[0], splitted[1]);
		}
		return query;
	}
}
