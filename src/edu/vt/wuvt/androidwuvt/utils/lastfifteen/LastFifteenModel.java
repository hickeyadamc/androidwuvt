package edu.vt.wuvt.androidwuvt.utils.lastfifteen;

public class LastFifteenModel {
	
	public String timeAired;
	public boolean isNew;
	public String track;
	public String album;
	public String dj;
	public boolean isRequest;
	public boolean isVinyl;
	
	// SETTERS
	public String getTimeAired() {
		return this.timeAired;
	}
	public boolean getIsNew() {
		return this.isNew;
	}
	public String getTrack() {
		return this.track;
	}
	public String getAlbum() {
		return this.album;
	}
	public String getDJ() {
		return this.dj;
	}
	public boolean getIsRequest() {
		return this.isRequest;
	}
	public boolean getIsVinyl() {
		return this.isVinyl;
	}
	
	//GETTERS
	public void setTimeAired(String timeAired) {
		this.timeAired = timeAired;
	}
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public void setDJ(String dj) {
		this.dj = dj;
	}
	public void setIsRequest(boolean isRequest) {
		this.isRequest = isRequest;
	}
	public void setIsVinyl(boolean isVinyl) {
		this.isVinyl = isVinyl;
	}
	
	
	
}
