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
		
		if(album == null){
			this.album = "";
		}
	}
	
	public Song(String title, String artist, String album){
		this.title = title;
		this.artist = artist;
		this.album = album;
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
	
	public String getYear(){
		String year; 
		if(this.year != 0){
			year = "" + this.year;
			return year;
		}
		else{
			return "";
		}		
	}
	
	
	public String toString(){
		return "{\n\tTitle: " + this.title + ", \n\tArtist: " + this.artist + ", \n\tAlbum: " + this.album + "\n\tYear: " + this.getYear() + "\n}";
	}
}
