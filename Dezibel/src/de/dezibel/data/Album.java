package de.dezibel.data;

import de.dezibel.io.ImageLoader;
// TODO: Eine Todo-Sache
public class Album extends Playlist {

	private String coverPath;

	private static ImageLoader imageLoader;

	public Album(Medium medium, String title, User user) {
            super(medium, title, user);
	}

	public ErrorCode uploadCover(String path) {
		return null;
	}

	public Image getCover() {
		return null;
	}

}
