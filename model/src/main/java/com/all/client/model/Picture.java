package com.all.client.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Picture {
	private static final int DEFAULT_SIZE = 218;
	private static final double PROPORTION = 0.45;
	private static final int MIN_SIZE = 47;
	private BufferedImage image;
	private String name;
	private Rectangle bounds;
	private int height;
	private int width;

	public Picture(File file) throws IOException {
		image = ImageIO.read(file);
		if (image == null) {
			throw new IllegalArgumentException("not an image");
		}
		name = file.getName();
		bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight());
		height = image.getHeight();
		width = image.getWidth();
	}

	public Image getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public Image getScaledImageShortestSide(ResizeImageType type) {
		if (!isValidImageSize()) {
			return null;
		}
		bounds = getScaledDimensionShortestSide(type);
		Rectangle scaledDimension = getScaledDimensionShortestSide(type);
		Dimension standardDimension = getStandardDimension(type);

		BufferedImage resizedImg = new BufferedImage(standardDimension.width, standardDimension.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(image, scaledDimension.x, scaledDimension.y, scaledDimension.width, scaledDimension.height, null);
		g2.dispose();
		// to free resources
		image = null;
		return resizedImg;
	}

	public Image getScaledImageLongestSide(ResizeImageType type) {
		if (!isValidImageSize()) {
			return null;
		}

		bounds = getScaledDimensionLongestSide(type);
		Rectangle scaledDimension = getScaledDimensionLongestSide(type);
		Dimension standardDimension = getStandardDimension(type);

		BufferedImage resizedImg = new BufferedImage(standardDimension.width, standardDimension.height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setColor(new Color(229, 229, 235));
		g2.fillRect(0, 0, standardDimension.width, standardDimension.height);
		g2.drawImage(image, scaledDimension.x, scaledDimension.y, scaledDimension.width, scaledDimension.height, null);
		g2.dispose();
		// writeImageToDisk(resizedImg, new File("test1.jpg"));
		return resizedImg;
	}

	public Image crop(Rectangle cropArea, ResizeImageType resizeImageType) {
		Rectangle scaledDimension = getScaledDimensionLongestSide(resizeImageType);
		BufferedImage scaledImageLongestSide = (BufferedImage) getScaledImageLongestSide(resizeImageType);
		BufferedImage cropImg = scaledImageLongestSide.getSubimage(scaledDimension.x + cropArea.x, scaledDimension.y
				+ cropArea.y, cropArea.width - 1, cropArea.height - 1);
		BufferedImage resizedImg = new BufferedImage(DEFAULT_SIZE, DEFAULT_SIZE, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.drawImage(cropImg, 0, 0, DEFAULT_SIZE, DEFAULT_SIZE, null);
		g2.dispose();
		
		return resizedImg;
	}

	private Rectangle getScaledDimensionShortestSide(ResizeImageType type) {
		float scaleFactor;
		int sourceHeight = image.getHeight();
		int sourceWidth = image.getWidth();

		Dimension standardDimension = getStandardDimension(type);

		int w = standardDimension.width;
		int h = standardDimension.height;

		Point traslate = new Point(0, 0);
		if (sourceHeight > sourceWidth) {
			scaleFactor = (float) w / sourceWidth;
			h = (int) (sourceHeight * scaleFactor + .5f);
			traslate.y -= Math.round((h - w) / 2.f);
		} else {
			scaleFactor = (float) h / sourceHeight;
			w = (int) (sourceWidth * scaleFactor + .5f);
			traslate.x -= Math.round((w - h) / 2.f);
		}
		return new Rectangle(traslate, new Dimension(w, h));
	}

	private Rectangle getScaledDimensionLongestSide(ResizeImageType type) {
		float scaleFactor;
		int sourceHeight = image.getHeight();
		int sourceWidth = image.getWidth();

		Dimension standardDimension = getStandardDimension(type);

		int w = standardDimension.width;
		int h = standardDimension.height;

		Point traslate = new Point(0, 0);
		if (sourceWidth > sourceHeight) {
			scaleFactor = (float) w / sourceWidth;
			h = (int) (sourceHeight * scaleFactor + .5f);
			traslate.y += Math.round((w - h) / 2.f);
		} else {
			scaleFactor = (float) h / sourceHeight;
			w = (int) (sourceWidth * scaleFactor + .5f);
			traslate.x += Math.round((h - w) / 2.f);
		}
		return new Rectangle(traslate, new Dimension(w, h));
	}

	private Dimension getStandardDimension(ResizeImageType type) {
		if (type == ResizeImageType.editMyInfo) {
			return new Dimension(96, 96);
		} else if (type == ResizeImageType.myInfoSection) {
			return new Dimension(42, 42);
		} else {
			return new Dimension(418, 418);
		}
	}


	public boolean isProportionedImage() {
		double min = Math.min(width, height);
		int max = Math.max(width, height);
		return min / max >= PROPORTION;
	}

	public boolean isValidImageSize() {
		return width > MIN_SIZE && height > MIN_SIZE;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
}