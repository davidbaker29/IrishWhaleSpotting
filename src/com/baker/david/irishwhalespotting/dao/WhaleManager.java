package com.baker.david.irishwhalespotting.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baker.david.irishwhalespotting.domain.Classification;
import com.baker.david.irishwhalespotting.domain.KeyIdentificationFeatures;
import com.baker.david.irishwhalespotting.domain.Whale;
import com.baker.david.irishwhalespotting.domain.whaletypes.WhaleNames;

/**
 * TODO: see what design patterns I can use here. The Factory or Builder?
 * e.g. Whale whale = WhaleFactory.getWhale("fin") and it gives
 * back a FinWhale object that extends a base Whale class  
 * 
 * @author ebakdav
 *
 */

public class WhaleManager {

	public static List<Whale> ALL_WHALES = new ArrayList<Whale>();
	
	public static Map<String, Whale> WHALE_MAP = new LinkedHashMap<String, Whale>();
	
	static {
		initWhales();
	}
		
	private static void initWhales() {
		
		Whale atlanticWhiteSideDolphin = WhaleFactory.buildWhale(WhaleNames.ATLANTIC_DOLPHIN);
		addWhale(atlanticWhiteSideDolphin);
		
		Whale belugaWhale = buildBelugaWhale();
		addWhale(belugaWhale);
		
		Whale blueWhale = buildBlueWhale();
		addWhale(blueWhale);
		
		Whale bottlenoseDolphin = buildBottlenoseDolphin();
		addWhale(bottlenoseDolphin);
		
		Whale commonDolphin = buildCommonDolphin();
		addWhale(commonDolphin);
		
		Whale cuviersBeakedWhale = buildCuviersBeakedWhale();
		addWhale(cuviersBeakedWhale);
		
		Whale falseKillerWhale = buildFalseKillerWhale();
		addWhale(falseKillerWhale);
		
		Whale finWhale = WhaleFactory.buildWhale(WhaleNames.FIN_WHALE);
		addWhale(finWhale);
		
		Whale humpbackWhale = buildHumpbackWhale();
		addWhale(humpbackWhale);
		
		Whale killerWhale = buildKillerWhale();
		addWhale(killerWhale);
	}

	private static Whale buildBelugaWhale() {
		Whale belugaWhale = new Whale("2", "Beluga Whale");
		belugaWhale.setImgFileName("beluga");
		
		return belugaWhale;
	}

	private static Whale buildBlueWhale() {
		Whale blueWhale = new Whale("3", "Blue Whale");
		blueWhale.setImgFileName("blue");
		return blueWhale;
	}
	
	private static Whale buildBottlenoseDolphin() {
		Whale bottlenoseDolphin = new Whale("4", "Bottlenose Dolphin");
		bottlenoseDolphin.setImgFileName("bottlenose");
		
		return bottlenoseDolphin;	
	}

	private static Whale buildCommonDolphin() {
		Whale commonDolphin = new Whale("5", "Common Dolphin");
		commonDolphin.setImgFileName("common");
		
		return commonDolphin;
	}
	
	private static Whale buildCuviersBeakedWhale() {
		Whale cuviersBeakedWhale = new Whale("6", "Cuvier's Beaked Whale");
		cuviersBeakedWhale.setImgFileName("cuvier");
		
		return cuviersBeakedWhale;
	}
	
	private static Whale buildFalseKillerWhale() {
		Whale falseKillerWhale = new Whale("7", "False Killer Whale");
		falseKillerWhale.setImgFileName("false_killer");
		
		return falseKillerWhale;
	}

	private static Whale buildHumpbackWhale() {
		Whale humpback = new Whale("9", "Humpback Whale");
		
		humpback.setImgFileName("humpback");
		
		Classification humpbackWhaleClassification = new Classification("Mammalia","Cetecea","Mysticeti",
				"Balaeonpteridae", "Megaptera", "novaeangliae", 
				"Humpback Whale", "Míol mór cruiteach; Míol mór dronnach");
		
		humpback.setClassification(humpbackWhaleClassification);
		
		Map<String, String> humpbackKeyIdFeatures = new LinkedHashMap<String,String>();
		
		humpbackKeyIdFeatures.put("Maximum body length:", "Adults range 11-16m in length, females slightly longer than males.");
		humpbackKeyIdFeatures.put("Blow:", "Broad and bushy, to 3m");
		humpbackKeyIdFeatures.put("Head Shape:", "Head broad. Series of fleshy knobs on rostrum, called tubercles, which also occur on the lower lip.");
		humpbackKeyIdFeatures.put("Baleen:", "Dark brown with dark grey bristles, 270-400 plates per side, up to 0.8m long");

		humpbackKeyIdFeatures.put("Dorsal fin: ", "Small, variable with broad base, raised hump in front and \"knuckles\" behind.");
		humpbackKeyIdFeatures.put("Colouration:", "Black with white on throat and belly. Variable amounts of white on the underside of flukes and both sides of flippers.");
		humpbackKeyIdFeatures.put("Body:", "The body is stout, with flat broad head. Flippers long (one-third the body length) often white or partly white, with knobs " +
				"on the leading edge. Flukes broad with irregular trailing edge. Ventral throat grooves, 12 - 36, extending at least to the navel");
		
		KeyIdentificationFeatures humpbackWhaleKeyIdFeatures = new KeyIdentificationFeatures(humpbackKeyIdFeatures);
		humpback.setKeyIdentificationFeatures(humpbackWhaleKeyIdFeatures);
		
		return humpback;
	}
	
	private static Whale buildKillerWhale() {
		Whale killer = new Whale("10", "Killer Whale");
		
		killer.setImgFileName("killer");
		
		return killer;
	}

	private static void addWhale(Whale whale) {
		ALL_WHALES.add(whale);
		WHALE_MAP.put(whale.getId(), whale);
	}
	
//	public static String[] getWhaleNames() {
//		String[] whaleNames = new String[ALL_WHALES.size()];
//		
//		for (int i = 0; i < ALL_WHALES.size(); i++){
//			Whale whale = ALL_WHALES.get(i);
//			whaleNames[i] = whale.getName();
//		}
//		
//		return whaleNames;
//	}
//	
	public static List<String> getWhaleNames() {
		List<String> whaleNames = new ArrayList<String>();
		
		for (Whale whale: ALL_WHALES){
			whaleNames.add(whale.getName());
		}
		
		return whaleNames;
	}

	public static Map<String, String> getContent(String whaleName,
			String selectedCategory) {
		
		Whale chosenWhale = null;
		Map<String,String> content = new LinkedHashMap<String,String>();
		
		Iterator<Whale> allWhales = ALL_WHALES.iterator();
		
		while (allWhales.hasNext()){
			Whale whale = allWhales.next();
			if (whale.getName().equalsIgnoreCase(whaleName)){
				chosenWhale = whale;
			}
		}
		
		if (chosenWhale == null){
			throw new RuntimeException("Whale with specified name not found in WhaleManager");
		}
		
		if (selectedCategory.equalsIgnoreCase("Classification")) {
			content.put("Class", chosenWhale.getClassification().getWhaleClass());
			content.put("Order", chosenWhale.getClassification().getOrder());
			content.put("Suborder", chosenWhale.getClassification().getSubOrder());
			content.put("Family", chosenWhale.getClassification().getFamily());
			content.put("Genus", chosenWhale.getClassification().getGenus());
			content.put("Species", chosenWhale.getClassification().getSpecies());
			content.put("Common names", chosenWhale.getClassification().getCommonName());
			content.put("Irish names", chosenWhale.getClassification().getIrishName());
		}
		else if (selectedCategory.equalsIgnoreCase("Key Identification Features")){
			Map<String, String> keyFeatures = 
					chosenWhale.getKeyIdentificationFeatures().getKeyIdentificationFeatures();
			
			Set<String> keys = keyFeatures.keySet();
			for (String key: keys){
				content.put(key, keyFeatures.get(key));
			}
		}
		
		return content;
	}
}
