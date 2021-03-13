package dmp.staffadmin.metier.enumeration;

public enum PointsCardinauxEnum 
{
	NORD("Nord"),
	SUD("Sud"),
	EST("Est"),
	OUEST("Ouest"),
	CENTRE("Centre"),
	NORD_EST("Nord-Est"),
	NORD_OUEST("Nord-Ouest"),
	SUD_EST("Sud-Est"),
	SUD_OUEST("Sud-Ouest");

	private String positionGeo;
	PointsCardinauxEnum(String positionGeo)
	{
		this.positionGeo = positionGeo;
	}
	public String toString() {return positionGeo;}
}
