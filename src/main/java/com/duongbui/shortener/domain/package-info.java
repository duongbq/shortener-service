package com.duongbui.shortener.domain;

/**
 * What is a Port?
 * A port is a consumer agnostic entry and exit point to/from the application. In many languages, it will be an interface.
 * For example, it can be an interface used to perform searches in a search engine.
 * In our application, we will use this interface as an entry and/or exit point with no knowledge of the concrete implementation
 * that will actually be injected where the interface is defined as a type hint.
 */

/**
 * What is an Adapter?
 * An adapter is a class that transforms (adapts) an interface into another.
 *
 * For example, an adapter implements an interface A and gets injected an interface B.
 * When the adapter is instantiated it gets injected in its constructor an object that implements interface B.
 * This adapter is then injected wherever interface A is needed and receives method requests that it transforms and proxies to the inner object that implements interface B.
 */
