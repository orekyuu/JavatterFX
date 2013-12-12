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
	 * @return 自身のインスタンス
	 */
	public StatusUpdateBuilder setImage(File image){
		file=image;
		return this;
	}

	/**
	 * つぶやきの内容を追加する
	 * @param text つぶやく内容
	 * @return 自身のインスタンス
	 */
	public StatusUpdateBuilder setText(String text){
		this.text=text;
		return this;
	}

	/**
	 * リプライ先を設定
	 * @param status リプライ先のつぶやき
	 * @return 自身のインスタンス
	 */
	public StatusUpdateBuilder setReplyID(Status status){
		if(status==null)return this;
		replyID = status.getId();
		return this;
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
