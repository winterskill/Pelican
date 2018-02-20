package winterskill.discord.pelican.commands;

import java.lang.reflect.Method;

public final class SimpleCommand {
	private final String       name;
	private final String       description;
	private final Object       object;
	private final Method       method;
	private final ExecutorType type;
	
	public SimpleCommand(String name, String description, ExecutorType type, Object object, Method method) {
		this.name        = name;
		this.description = description;
		this.object      = object;
		this.method      = method;
		this.type        = type;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final String getDescription() {
		return this.description;
	}
	
	public final Object getObject() {
		return this.object;
	}
	
	public final Method getMethod() {
		return this.method;
	}
	
	public final ExecutorType getType() {
		return this.type;
	}
}
