package xyz.pierini.rankings.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum PlayerRankingEnum {
	
	ONE_ONE("1.1"),
	TWO_ONE("2.1"),
	TWO_TWO("2.2"),
	TWO_THREE("2.3"),
	TWO_FOUR("2.4"),
	THREE_ONE("3.1"),
	THREE_TWO("3.2"),
	THREE_THREE("3.3"),
	THREE_FOUR("3.4"),
	FOUR_ONE("4.1"),
	FOUR_TWO("4.2"),
	FOUR_THREE("4.3"),
	FOUR_FOUR("4.4"),
	FOUR_NC("4.NC");

	String value;
	
    private static final Map<String, PlayerRankingEnum> BY_LABEL = new HashMap<>();
    
    static {
        for (PlayerRankingEnum e: values()) {
            BY_LABEL.put(e.value, e);
        }
    }
	
	PlayerRankingEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
    public static PlayerRankingEnum valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
	
}
