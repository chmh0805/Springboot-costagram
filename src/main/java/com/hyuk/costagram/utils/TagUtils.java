package com.hyuk.costagram.utils;

import java.util.ArrayList;
import java.util.List;

import com.hyuk.costagram.domain.image.Image;
import com.hyuk.costagram.domain.tag.Tag;

public class TagUtils {
	
	public static List<Tag> parsingToTagObject(String tags, Image imageEntity) {
		String temp[] = tags.split("#");
		
		List<Tag> list = new ArrayList<>();

		for (String tagName : temp) {
			if (!tagName.equals("")) {
				Tag tag = Tag.builder()
						.name(tagName.trim())
						.image(imageEntity)
						.build();
				list.add(tag);				
			}
		}
		
		return list;
	}
}
