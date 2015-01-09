package eu.factorx.citizens.model.type;

public enum QuestionCode {
	// household
	Q1300,
	Q1400,
	Q1500,
	Q1110(1300),
	Q1120(2000),
	Q1130(3500),
	Q1600(70),
	Q1210(250),
	Q1900,
	Q1160(1100),
	Q1220(60),
	Q1230(40),
	Q1700(180),
	Q1750(250),
	Q1800,
	Q2010(700),
	Q2020(600),
	Q2030(1200),
	Q2040(1800),
	Q1235(4000),
	Q1140(3500),
	Q1150(2000),
	Q3210,
	Q3211,
	Q3110,
	Q3120,
	Q3130,
	Q3310,
	Q3320,
	Q3330,
	Q3410,
	Q3420,
	Q3510,
	Q3530,
	Q3610,
	Q3620,
	Q3630,
	Q3631,
	Q3640,
	Q3810,
	Q3710,
	Q3711,
	Q3720,
	Q3730,
	Q3740(2500),
	Q3741,
	Q3750,
	Q3760,
	Q9999,

	// enterprise
	Q10110,
	Q10120,
	Q10210,
	Q10220,
	Q10230,
	Q10240,
	Q10250,
	Q10260,
	Q10270,
	Q10280,
	Q10290,
	Q10310,
	Q10320,
	Q10330,
	Q10340,
	Q10350,
	Q10360,
	Q10380,
	Q10390,
	Q10395,
	Q10400;


	/**
	 * Nominal power of the electrical machine on which the question relates, in Watt
	 */
	private Integer nominalPower = null;

	QuestionCode() {
	}

	QuestionCode(Integer nominalPower) {
		this.nominalPower = nominalPower;
	}

	public Integer getNominalPower() {
		return nominalPower;
	}

}
