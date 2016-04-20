package com.demo.core.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicInvocation implements InvocationHandler {

	private Class<?> clazz;

	public DynamicInvocation(Class<?> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public <T> T getProxy() {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		return method.invoke(clazz.newInstance(), args);
	}

}
