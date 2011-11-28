package com.all.shared.model;

import java.io.Serializable;

public class ModelSource implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum ModelSourceType {
		REMOTE, LOCAL, TOP_HUNDRED, NEWSFEED;
	}

	private final ModelSourceType type;
	private Category hundredCategory;
	private Playlist hundredPlaylist;
	private ContactInfo contact;

	private ModelSource(ModelSourceType type) {
		this.type = type;
	}

	public static ModelSource local() {
		return new ModelSource(ModelSourceType.LOCAL);
	}

	public static ModelSource remote() {
		return new ModelSource(ModelSourceType.REMOTE);
	}

	public static ModelSource topHundred(Category category, Playlist playlist) {
		if (category == null) {
			return remote();
		}
		ModelSource modelSource = new ModelSource(ModelSourceType.TOP_HUNDRED);
		modelSource.hundredCategory = new SimpleCategory(category);
		if (playlist != null) {
			modelSource.hundredPlaylist = new RemotePlaylist(playlist);
		}
		return modelSource;
	}

	public static ModelSource newsfeed(ContactInfo contact) {
		ModelSource modelSource = new ModelSource(ModelSourceType.NEWSFEED);
		modelSource.contact = contact;
		return modelSource;
	}

	public Category getHundredCategory() {
		return hundredCategory;
	}

	public Playlist getHundredPlaylist() {
		return hundredPlaylist;
	}

	public ModelSourceType getType() {
		return type;
	}

	public ContactInfo getContact() {
		return contact;
	}

}
