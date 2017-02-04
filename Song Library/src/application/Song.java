package application;

public class Song {
	private String title;
	private String artist;
	private String album;
	private int year;
	
	public Song(String title, String artist, String album, int year){
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	
	public void setArtist(String artist){
		this.artist = artist;
	}
	
	public String getArtist(){
		return artist;
	}
	
	
	
	public void setAlbum(String album){
		this.album = album;
	}
	
	public String getAlbum(){
		return album;
	}
	
	
	public void setYear(int year){
		this.year = year;
	}
	
	public int getYear(){
		return year;
	}
}
