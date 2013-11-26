package com.github.orekyuu.javatterfx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.github.orekyuu.javatterfx.account.AccountManager;
import com.github.orekyuu.javatterfx.account.TwitterManager;

public class LoginController implements Initializable{

	@FXML
	private BorderPane root;
    @FXML
    private BorderPane border;
    @FXML
    private TextField text;
    @FXML
    private Button button;

    private RequestToken token;

    private Twitter twitter;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		twitter=TwitterManager.getInstance().getTwitter();
	}

	public void browse(Application app){
		try {
			token=twitter.getOAuthRequestToken();
			app.getHostServices().showDocument(token.getAuthenticationURL());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void authentication(ActionEvent event) {
		String pin=text.getText();
		AccessToken t;
		try {
			t = twitter.getOAuthAccessToken(this.token, pin);
			AccountManager.getInstance().setAccessToken(t);
			TwitterManager.getInstance().authentication(t);
			TwitterManager.getInstance().getTwitter().setOAuthAccessToken(t);
			button.getScene().getWindow().hide();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

}
