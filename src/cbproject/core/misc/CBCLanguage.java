package cbproject.core.misc;

import net.minecraft.util.StringTranslate;
import cbproject.core.register.CBCBlocks;
import cbproject.core.register.CBCItems;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CBCLanguage {
	
	public static String currentLanguage = StringTranslate.getInstance().getCurrentLanguage();
	private static LanguageRegistry i = LanguageRegistry.instance();
	
	public static void addLocalName(Object objectToName, String name){
		System.out.println(currentLanguage);
		if(currentLanguage != "en_US"){
			if(objectToName instanceof String)
				i.addStringLocalization((String)objectToName, currentLanguage, name);
			else i.addNameForObject(objectToName, currentLanguage, name);
		}
	}
	
	public static void addDefaultName(Object objectToName, String name){
		if(objectToName instanceof String)
			i.addStringLocalization((String) objectToName, name);
		else i.addName(objectToName, name);
	}

}
