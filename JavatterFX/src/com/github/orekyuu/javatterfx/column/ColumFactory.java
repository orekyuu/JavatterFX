package com.github.orekyuu.javatterfx.column;

import javafx.scene.Parent;

public interface ColumFactory {

	public String getColumName();

	public Parent createView();
}
