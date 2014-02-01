package com.baker.david.irishwhalespotting.domain;

import java.util.Map;

public class KeyIdentificationFeatures {

	private Map<String,String> keyIdentificationFeatures;
	
	public KeyIdentificationFeatures(
			Map<String, String> keyIdentificationFeatures) {

		this.keyIdentificationFeatures = keyIdentificationFeatures;
	}

	public Map<String, String> getKeyIdentificationFeatures() {
		return keyIdentificationFeatures;
	}

	public void setKeyIdentificationFeatures(
			Map<String, String> keyIdentificationFeatures) {
		this.keyIdentificationFeatures = keyIdentificationFeatures;
	}
	
	
}
