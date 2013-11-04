package com.github.orekyuu.javatterfx.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.util.IconCache;

public class UserInfoTabController implements Initializable{

	@FXML
	private ImageView img;
	@FXML
	private Button follow;
	@FXML
	private Label followLabel;
	@FXML
	private Label account;
	@FXML
	private Label userName;
	@FXML
	private Hyperlink hp;
	@FXML
	private Label location;
	@FXML
	private Label description;
	@FXML
	private Label tweetCount;
	@FXML
	private Label favCount;
	@FXML
	private Label followCount;
	@FXML
	private Label followerCount;

	private User user;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * ユーザーを設定し、テキストを初期化する
	 * @param user
	 */
	public void setUser(User user){
		this.user=user;
		try {
			URL url = new URL(user.getProfileImageURL());
			img.setImage(IconCache.getInstance().getIcon(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Twitter twitter=TwitterManager.getInstance().getTwitter();
		long myID=TwitterManager.getInstance().getUser().getId();
		long userID=user.getId();

		try {
			if(twitter.showFriendship(myID, userID).isSourceFollowingTarget()){
				follow.setText("フォロー解除");
			}else{
				follow.setText("フォローする");
			}

			if(twitter.showFriendship(userID, myID).isSourceFollowingTarget()){
				followLabel.setText("フォローされている");
			}else{
				followLabel.setText("フォローされていません");
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		account.setText("@"+user.getScreenName());
		userName.setText(user.getName());
		hp.setText(user.getURL());
		location.setText(user.getLocation());
		description.setText(user.getDescription());
		tweetCount.setText(Integer.toString(user.getStatusesCount()));
		favCount.setText(Integer.toString(user.getFavouritesCount()));
		followCount.setText(Integer.toString(user.getFriendsCount()));
		followerCount.setText(Integer.toString(user.getFollowersCount()));

	}

	public void onFollowClick(ActionEvent event){
		Twitter twitter=TwitterManager.getInstance().getTwitter();
		long myID=TwitterManager.getInstance().getUser().getId();
		long userID=user.getId();
		try {
			if(twitter.showFriendship(myID, userID).isSourceFollowingTarget()){
				twitter.destroyFriendship(userID);
				follow.setText("フォローする");
			}else{
				twitter.createFriendship(userID);
				follow.setText("フォロー解除");
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void HPClick(ActionEvent event){
		try {
			Desktop.getDesktop().browse(new URL(hp.getText()).toURI());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
