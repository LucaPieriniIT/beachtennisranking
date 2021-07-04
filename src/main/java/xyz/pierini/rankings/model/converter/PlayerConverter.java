package xyz.pierini.rankings.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import xyz.pierini.rankings.enumerator.PlayerRankingEnum;
import xyz.pierini.rankings.enumerator.YearEnum;

public class PlayerConverter {

	@ReadingConverter
	// TODO @WritingConverter
	public enum PlayerRankingEnumConverter implements Converter<String, PlayerRankingEnum> {
		INSTANCE;
		public PlayerRankingEnum convert(String source) {
			return PlayerRankingEnum.valueOfLabel(source);
		}
		
	}
	
	@ReadingConverter
	// TODO @WritingConverter
	public enum YearEnumConverter implements Converter<String, YearEnum> {
		INSTANCE;
		public YearEnum convert(String source) {
			return YearEnum.valueOfLabel(source);
		}
		
	}
	
}
