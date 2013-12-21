package com.github.orekyuu.javatterfx.managers;

import javafx.scene.Parent;

public interface ColumFactory {

	public String getColumName();

	public Parent createView();
}
