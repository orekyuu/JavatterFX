package orekyuu.plugin.loader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * プラグインのエントリポイントにつける
 * @author kyuuban
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

	/**
	 * プラグインの名前
	 * @return
	 */
	String name();
	/**
	 * プラグインのバージョン
	 * @return
	 */
	String version();

	/**
	 * プラグインの初期化
	 * @author kyuuban
	 *
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Init{}

	/**
	 * このアノテーションをつけるとPlugin.Initの前のタイミングで呼び出されます
	 * @author kyuuban
	 *
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface PreInit{}

	/**
	 * このアノテーションをつけるとPlugin.Initの後のタイミングで呼び出されます
	 * @author kyuuban
	 *
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface PostInit{}

	/**
	 * コンフィグに表示するMenuItemのインスタンスが入るフィールドにアノテーションを付けてください<br>
	 * 型はMenuItemかMenuのどちらかにしてください
	 * @author kyuuban
	 *
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ConfigItem{}
}
