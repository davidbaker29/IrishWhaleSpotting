package com.baker.david.irishwhalespotting.domain.whaletypes;

import java.util.LinkedHashMap;
import java.util.Map;

import com.baker.david.irishwhalespotting.domain.Classification;
import com.baker.david.irishwhalespotting.domain.KeyIdentificationFeatures;
import com.baker.david.irishwhalespotting.domain.Whale;

public class AtlanticWhiteSidedDolphin extends Whale {

	public AtlanticWhiteSidedDolphin(){
		
		this.setId("1");
		this.setName("Atlantic White-Sided Dolphin");
		this.setImgFileName("atlantic_dolphin");
		
		Classification atlanticDolphinClassification = new Classification("Mammalia","Cetecea","Odontoceti",
				"Delphinidae", "Lagenorhynchus", "acutus", 
				"Atlantic white-sided dolphin", "Deilf le cliathán bán");
		
		this.setClassification(atlanticDolphinClassification);
		
		Map<String, String> atlanticDolphinKeyIdFeaturesMap = new LinkedHashMap<String,String>();
		
		atlanticDolphinKeyIdFeaturesMap.put("Maximum body length", "Adult male 2.8m, female 2.5m");
		atlanticDolphinKeyIdFeaturesMap.put("Average body length", "Adult 2.4 - 2.8m");
		atlanticDolphinKeyIdFeaturesMap.put("Head shape", "Gently sloping forehead");
		atlanticDolphinKeyIdFeaturesMap.put("Beak", "Beak is short, black above and white below. Teeth are sharp and conical, " +
				"58 - 80 in the upper row, 58 - 80 lower jaw");
		atlanticDolphinKeyIdFeaturesMap.put("Dorsal fin ", "Tall, falcate, sharply pointed with narrow base, located mid-back. " +
				"Their name derives from the Latin acutus (\"sharp\"), referring to the acutely pointing dorsal fin");
		atlanticDolphinKeyIdFeaturesMap.put("Colouration", "Black back, top of beak, flippers and flukes. Grey sides, white belly. " +
				"White band below dorsal fin connects with yellow band on tailstock. ");
		atlanticDolphinKeyIdFeaturesMap.put("Body", "The body is robust, with a gently sloping forehead and the tailstock is strongly truncated. " +
				"Flippers are sickle shaped and pointed.");
		
		KeyIdentificationFeatures atlanticDolphinKeyIdFeatures = new KeyIdentificationFeatures(atlanticDolphinKeyIdFeaturesMap);
		this.setKeyIdentificationFeatures(atlanticDolphinKeyIdFeatures);
	}
}
