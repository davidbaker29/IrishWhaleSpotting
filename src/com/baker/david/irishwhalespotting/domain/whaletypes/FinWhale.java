package com.baker.david.irishwhalespotting.domain.whaletypes;

import java.util.LinkedHashMap;
import java.util.Map;

import com.baker.david.irishwhalespotting.domain.Classification;
import com.baker.david.irishwhalespotting.domain.KeyIdentificationFeatures;
import com.baker.david.irishwhalespotting.domain.Whale;

public class FinWhale extends Whale {

	public FinWhale() {

		this.setId("8");
		this.setName("Fin Whale");
		this.setImgFileName("fin");
		
		Classification finWhaleClassification = new Classification("Mammalia","Cetecea","Mysticeti",
				"Balaenopteridae", "Balaenoptera", "physalus", 
				"Fin whale; Finback; Herring Hog", "Míol mór eiteach");
		
		this.setClassification(finWhaleClassification);
		
		Map<String, String> finKeyIdFeatures = new LinkedHashMap<String,String>();
		
		finKeyIdFeatures.put("Maximum body length", "Adult male 22m (72ft), adult female 24m (80ft)");
		finKeyIdFeatures.put("Record length", "27m (88.5ft)");
		finKeyIdFeatures.put("Average body length", "Adult male 19m (62ft), adult female 20.5m (67ft)");
		finKeyIdFeatures.put("Blow", "Tall, robust 6m (20ft) column, visible at great distances on windless days");
		finKeyIdFeatures.put("Head shape", "V-shaped, top is flat, single prominent median/rostral ridge");
		finKeyIdFeatures.put("Dorsal fin ", "Not diagnostic, as highly variable. Small (60cm), falcate, " +
				"backwards sloping, angled <40deg, located two-thirds along back and appears shortly after blow.");
		finKeyIdFeatures.put("Colouration", "Dark grey to brownish black on back and sides");
		finKeyIdFeatures.put("Markings", "Right lower lip including mouth cavity are whitish, but lips and " +
				"baleen on left side are all dark. This asymmetrical head pigmentation is unique and therefore " +
				"diagnostic. Right anterior 20-30% baleen plates are yellowish/white. Two chevrons originate " +
				"behind blowhole and run aft forming broad \"V\" along back. Broad pale wash sweeps up from corner" +
						" of jaw to behind blowhole.");
		
		KeyIdentificationFeatures finWhaleKeyIdFeatures = new KeyIdentificationFeatures(finKeyIdFeatures);
		this.setKeyIdentificationFeatures(finWhaleKeyIdFeatures);
	}
	

}
