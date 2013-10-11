package com.github.orekyuu.javatterfx.util;

import java.io.File;

import twitter4j.Status;
import twitter4j.StatusUpdate;

public class StatusUpdateBuilder {

	private String text;
	private long replyID;
	private File file;
	public StatusUpdateBuilder(String text){
		this.text=text;
	}

	/**
	 * つぶやきに画像を添付する
	 * @param image 添付したい画像のパス
	 */
	public void setImage(File image){
		file=image;
	}

	/**
	 * つぶやきの内容を追加する
	 * @param text つぶやく内容
	 */
	public void setText(String text){
		this.text=text;
	}

	/**
	 * リプライ先を設定
	 * @param status リプライ先のつぶやき
	 */
	public void setReplyID(Status status){
		if(status==null)return;
		replyID = status.getId();
	}

	/**
	 * StatusUpdateを作成
	 */
	public StatusUpdate create(){
		StatusUpdate status=new StatusUpdate(text);
		status.setInReplyToStatusId(replyID);
		status.setMedia(file);
		return status;
	}
}
