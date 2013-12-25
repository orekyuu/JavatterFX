package com.github.orekyuu.javatterfx.controller;

import twitter4j.Status;

public interface IStatusList {

	void addStatusTop(Status status);

	void addStatusLast(Status status);

	void removeStatus(Status status);

	void scrollTop();
}
