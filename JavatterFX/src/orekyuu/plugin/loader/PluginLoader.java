package orekyuu.plugin.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader{

	public void load(){
		addPlugin(orekyuu.plugin.loader.plugin.TestPlugin.class);
		pluginPostInit();
	}

	/**
	 * ���ׂẴv���O�C����PostInit���Ăяo��
	 */
	private void pluginPostInit(){
		for(Object obj:PluginRegister.INSTANCE.getPluginList()){
			for(Method m:obj.getClass().getMethods()){
				if(equippedAnnotations(m.getAnnotations(), Plugin.PostInit.class)==null)
					continue;
				try {
					m.invoke(obj, null);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �v���O�C���ɒǉ�
	 * @param clazz
	 */
	private void addPlugin(Class clazz){
		Annotation plugin=getPluginAnnotation(clazz);//�N���X��Plugin�A�m�e�[�V�������擾
		if(plugin==null)return ;//�A�m�e�[�V�������Ȃ���Βǉ����Ȃ�
		Plugin p=(Plugin) plugin;

		try {
			Object obj=clazz.newInstance();//�C���X�^���X�𐶐�

			//PreInit�A�m�e�[�V�������t������Ă��郁�\�b�h�����s����
			for(Method m:clazz.getMethods()){
				if(equippedAnnotations(m.getAnnotations(),
						orekyuu.plugin.loader.Plugin.PreInit.class)!=null){
						m.invoke(obj, (Object[])null);
				}
			}
			PluginRegister.INSTANCE.registerPlugin(p.name(), obj);//�v���O�C����o�^����
			//Init�A�m�e�[�V�������t������Ă��郁�\�b�h�����s����
			for(Method m:clazz.getMethods()){
				if(equippedAnnotations(m.getAnnotations(),
						orekyuu.plugin.loader.Plugin.Init.class)!=null){
						m.invoke(obj, (Object[])null);
				}
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �w�肳�ꂽ�N���X��Plugin�A�m�e�[�V������Ԃ��B�Ȃ����null
	 * @param target ���ׂ�N���X
	 * @return
	 */
	private Annotation getPluginAnnotation(Class target){
		Annotation[] list=equippedAnnotations(target.getAnnotations(), Plugin.class);
		if(list!=null){
			return list[0];
		}
		return null;
	}

	/**
	 * �z��̒��Ɏw�肳�ꂽ�A�m�e�[�V���������邩���ׂ�
	 * @param annotations ���ׂ�z��
	 * @param target �A�m�e�[�V�����̃N���X
	 * @return ���������A�m�e�[�V����
	 */
	private Annotation[] equippedAnnotations(Annotation[] annotations,Class target){
		List<Annotation> list=new ArrayList<Annotation>();
		for(Annotation a:annotations){
			if(a.annotationType().equals(target)){
				list.add(a);
			}
		}
		if(list.size()==0)return null;
		return list.toArray(new Annotation[]{});
	}
}