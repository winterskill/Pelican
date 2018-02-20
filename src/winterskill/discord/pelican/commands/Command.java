package winterskill.discord.pelican.commands;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Command {
	public String name();
	public String description() default "<THIS COMMAND HAVE NO DESCRIPTION>";
	public ExecutorType type() default ExecutorType.ALL;
}
