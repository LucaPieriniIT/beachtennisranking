package xyz.pierini.rankings.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Data;
import xyz.pierini.rankings.enumerator.GenderEnum;
import xyz.pierini.rankings.enumerator.PlayerRankingEnum;
import xyz.pierini.rankings.enumerator.YearEnum;

@Data
@Document
@CompoundIndex(name = "genderAndYearIndex", def = "{'gender': 1, 'year': 1}")
public class Player {

	@Id
	private String id;
	
	private String fullName;
	
	private String cityCode;
	
	private PlayerRankingEnum previousRanking;
	
	private PlayerRankingEnum currentRanking;
	
	private String note;
	
	@Field(targetType = FieldType.DECIMAL128)
	private BigDecimal points;
	
	private GenderEnum gender;
	
	private YearEnum year;
	
}
