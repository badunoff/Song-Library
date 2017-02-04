package application;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class SongLibrary {
	private HashMap<String, Song> library;
	Set<String> keys;
	
	public SongLibrary()
	{
		library = new HashMap<String, Song>();
		keys = new TreeSet<String>();
	}
	

	
	
	
	
	public void add(String title, String artist, String album, int year) throws Exception{
		String key = strProcess(title).concat(strProcess(artist));
		
		if(library.containsKey(key)){
			throw new Exception("Song already exists in song library");
		}
		
		Song song = new Song(title, artist, album, year);		
	}
	
	public void add(String title, String artist, String album) throws Exception{
		String key = generateKey(title, artist);
		
		if(library.containsKey(key)){
			throw new Exception("Song already exists in song library");
		}
		
		Song song = new Song(title, artist, album);		
		
		addSong(key, song);
	}
	
	private void addSong(String key, Song song){
		library.put(key, song);
		keys.add(key);
	}
	
	
	
	
	
	
	
	//returns true if song deleted.
	//returns false if song not in library.
	public boolean deleteSong(String title, String artist){
		String key = generateKey(title, artist);
		
		if(!library.containsKey(key))
			return false;
		
		library.remove(key);
		keys.remove(key);
		
		return true;
	}
	
	
	
	
	
	
	
	
	public void edit(String orig_title, String orig_artist, String title, String artist, String album, int year){
		deleteSong(orig_title, orig_artist);
		try {
			add(title, artist, album, year);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void edit(String orig_title, String orig_artist, String title, String artist, String album){
		deleteSong(orig_title, orig_artist);
		try {
			add(title, artist, album);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	private String generateKey(String title, String artist){
		return strProcess(title).concat(strProcess(artist));
	}
	
	
	private String strProcess(String str){
		return str.trim().toLowerCase();
	}
}
