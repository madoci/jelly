package fr.uga.fran;

public interface DataframeViewer {
	public String view(Dataframe dataframe);
	public String head(Dataframe dataframe);
	public String head(Dataframe dataframe, int num);
	public String tail(Dataframe dataframe);
	public String tail(Dataframe dataframe, int num);
}
