package com.all.shared.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AvatarUtils {

	private static int AVATAR_WIDTH = 48;
	private static int AVATAR_HEIGHT = 48;

	public byte[] resizeAvatar(byte[] fileData)	{
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
                BufferedImage img = ImageIO.read(in);
                if(AVATAR_HEIGHT == 0) {
                        AVATAR_HEIGHT = (AVATAR_WIDTH * img.getHeight())/ img.getWidth(); 
                }
                if(AVATAR_WIDTH == 0) {
                        AVATAR_WIDTH = (AVATAR_HEIGHT * img.getWidth())/ img.getHeight();
                }
                Image scaledImage = img.getScaledInstance(AVATAR_WIDTH, AVATAR_HEIGHT, Image.SCALE_SMOOTH);
                BufferedImage imageBuff = new BufferedImage(AVATAR_WIDTH, AVATAR_HEIGHT, BufferedImage.TYPE_INT_RGB);
                imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                ImageIO.write(imageBuff, "jpg", buffer);

                return buffer.toByteArray();
        } catch (IOException e) {
                return fileData;
        }
    }
}