/**
 * 
 */
package com.all.shared.model;

import java.util.HashSet;
import java.util.Set;

public enum ModelTypes {
	tracks, playlists, folders, smartPlaylists, others;
	public static ModelTypes[] invert(ModelTypes... types) {
		Set<ModelTypes> invertedTypes = new HashSet<ModelTypes>();
		for (ModelTypes mt : ModelTypes.values()) {
			invertedTypes.add(mt);
		}
		for (ModelTypes mt : types) {
			invertedTypes.remove(mt);
		}
		return invertedTypes.toArray(new ModelTypes[] {});
	}
}