package application;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import application.view.LayoutController;

public class SongLibrary {
	private HashMap<String, Song> library;
	private Set<String> keys;
	
	public SongLibrary()
	{
		library = new HashMap<String, Song>();
		keys = new TreeSet<String>();
	}
	
	
	
	public Song getSong(String title, String artist){
		return library.get(generateKey(title, artist));
	}
	
	public Song getSong(String key){
		return library.get(key);
	}

	public Set<String> getKeys(){
		return keys;
	}
	
	
	
	
	
	
	
	public Song add(String title, String artist, String album, int year) throws Exception{
		String key = generateKey(title, artist);
		
		if(library.containsKey(key)){
			throw new Exception("Trying to Add Existing Key");
		}
		
		Song song = new Song(title, artist, album, year);
		
		addSong(key, song);
		
		return song;
	}
	
	public Song add(String title, String artist, String album) throws Exception{
		String key = generateKey(title, artist);
		
		if(library.containsKey(key)){
			throw new Exception("Trying to Add Existing Key");
		}
		
		Song song = new Song(title, artist, album);		
		
		addSong(key, song);
		
		return song;
	}
	
	private void addSong(String key, Song song){
		library.put(key, song);
		keys.add(key);
	}
	
	
	
	
	public Song edit(String orig_title, String orig_artist, String title, String artist, String album, int year) throws Exception{
		deleteSong(orig_title, orig_artist);
		return add(title, artist, album, year);
	}
	
	public Song edit(String orig_title, String orig_artist, String title, String artist, String album) throws Exception{
		deleteSong(orig_title, orig_artist);
		return add(title, artist, album);
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
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	private String generateKey(String title, String artist){
		return strProcess(title).concat(" - " + strProcess(artist));
	}
	
	
	private String strProcess(String str){
		return str.trim().toLowerCase();
	}
	
}
