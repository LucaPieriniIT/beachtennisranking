package xyz.pierini.rankings.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum YearEnum {

	TWENTYONE("2021");
	
	String value;
	
    private static final Map<String, YearEnum> BY_LABEL = new HashMap<>();
    
    static {
        for (YearEnum e: values()) {
            BY_LABEL.put(e.value, e);
        }
    }
	
	YearEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
    public static YearEnum valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
	
}
